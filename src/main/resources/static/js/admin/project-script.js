/*global document console location axios $ confirm event DOMParser*/

document.addEventListener( "DOMContentLoaded", function () {
    addListenersToEditBtns();
    addListenersToDeleteBtns();
    addListenerToSizeSelect();
    addListenerToSortSelect();
} );


async function addListenersToEditBtns() {
    const editBtns = document.querySelectorAll( "[id^='edit__btn']" );
    for ( const btn of editBtns ) {
        btn.addEventListener( "click", function () {
            const idTxt = btn.closest( "tr" ).children[ 0 ].textContent.trim();
            console.log( idTxt );
            const id = parseInt( idTxt );
            console.log( idTxt );
            axios.get( `${ location.href }/projects/${ id }` )
                .then( response => {
                    const obj = JSON.parse( JSON.stringify( response.data ) );
                    const $form = $( "#project__form" )
                        .find( "#project_id__input" ).val( obj.id ).end()
                        .find( "#project_title" ).val( obj.title ).end()
                        .find( "#project_description" ).val( obj.description ).end();
                    if ( obj.picturePath ) {
                        $form.children( "form #project__img" ).attr( "src", obj.picturePath );
                    }
                } )
                .catch( error => console.error( error ) );
        } );
    }
}

async function addListenersToDeleteBtns() {
    const deleteBtns = document.querySelectorAll( "[id^='delete__btn']" );
    for ( const btn of deleteBtns ) {
        btn.addEventListener( "click", function () {
            const tableRow = event.target.closest( "tr" );
            const idTxt = tableRow.textContent.trim();
            const id = parseInt( idTxt );
            if ( confirm( "Ви впевнені що хочете видалити інформацію про проект" ) ) {
                axios.delete( `${ location.href }/projects/delete/${ id }` )
                    .then( responce => {
                        console.log( responce.data );
                        $( tableRow ).fadeOut( 250, () => {
                            $( tableRow ).remove();
                        } );
                    } )
                    .catch( error => console.error( error.message ) );
            }
        } );
    }
}

async function addListenerToSizeSelect() {
    document.getElementById( "size__select" ).addEventListener(
        "change",
        function ( event ) {
            const page = document.querySelector( "#pagination li.active" ).textContent.trim();
            const size = event.target.value;
            const col = document.getElementById( "sort__select" ).value;
            doPagination( page, size, col );
        } );
}

async function addListenerToSortSelect() {
    document.getElementById( "sort__select" ).addEventListener(
        "change",
        function ( event ) {
            const page = document.querySelector( "#pagination li.active" ).textContent.trim();
            const size = document.getElementById( "size__select" ).value;
            const col = event.target.value;
            doPagination( page, size, col );
        } );
}

// eslint-disable-next-line no-unused-vars
function goToPage( page ) {
    const size = document.getElementById( "size__select" ).value;
    const col = document.getElementById( "sort__select" ).value;
    doPagination( page, size, col );
}

async function doPagination( page, size, col ) {
    axios.get( `${ location.href }/projects?page=${ page }&size=${ size }&col=${ col }` )
        .then( responce => {
            console.log( `page=${ page }; size=${ size }; col=${ col };` );
            const newPage = new DOMParser().parseFromString( responce.data, "text/html" );

            const newTBody = newPage.querySelector( "section>table>tbody" );
            console.log( newTBody );
            $( "section>table>tbody" ).fadeOut( 250, function () {
                $( this ).remove();
                $( "section>table" ).append( newTBody ).attr( "display", "none" ).fadeIn( 250 );
                addListenersToEditBtns();
                addListenersToDeleteBtns();
            } );

            const newNav = newPage.querySelector( "#pagination>nav" );
            console.log( newNav );
            $( "#pagination>nav" ).fadeIn( 100, function () {
                $( this ).remove();
                $( "#pagination" ).append( newNav ).attr( "display", "none" ).fadeIn( 100 );
            } );
        } )
        .catch( error => console.error( error ) );
}
