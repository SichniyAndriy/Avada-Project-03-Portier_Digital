/*global document console alert location axios $ confirm event Event DOMParser FormData*/


document.addEventListener( "DOMContentLoaded", function () {
    addListenersToDeleteBtns();
    addListenerToSaveFormBtn();
    addListenerToCloseModalBtns();
    addListenerToSizeSelect();
    addListenerToSortSelect();
} );


async function addListenersToDeleteBtns() {
    const deleteBtns = document.querySelectorAll( "[id^='delete__btn']" );
    for ( const btn of deleteBtns ) {
        btn.addEventListener( "click", function () {
            const tableRow = event.target.closest( "tr" );
            const idTxt = tableRow.textContent.trim();
            const id = parseInt( idTxt );
            if ( confirm( "Ви впевнені що хочете видалити інформацію про проект" ) ) {
                axios.delete( `${ location.href }/delete/${ id }` )
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

async function addListenerToSaveFormBtn() {
    document.getElementById( "save_changes__btn" ).addEventListener( "click", async function () {
        const form = document.getElementById( "subscriber__form" );
        const formData = new FormData( form );
        axios.post( `${ location.href }/save`, formData )
            .then( responce => {
                console.log( "Id: " + responce.data );
                console.log( "Дані збережено" );
                document.getElementById( "close_modal__btn" ).dispatchEvent( new Event( "click" ) );
            } )
            .catch( error => alert( error.message ) );
    } );
}

async function addListenerToCloseModalBtns() {
    document.getElementById( "close_modal__btn" ).addEventListener( "click", function () { clearForm(); } );
    document.getElementById( "up_close_modal__btn" ).addEventListener( "click", function () { clearForm(); } );
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

async function doPagination( page, size, col ) {
    axios.get( `${ location.href }?page=${ page }&size=${ size }&col=${ col }` )
        .then( responce => {
            console.log( `page=${ page }; size=${ size }; col=${ col };` );
            const newPage = new DOMParser().parseFromString( responce.data, "text/html" );

            const newTBody = newPage.querySelector( "section>table>tbody" );
            console.log( newTBody );
            $( "section>table>tbody" ).fadeOut( 250, function () {
                $( this ).remove();
                $( "section>table" ).append( newTBody ).attr( "display", "none" ).fadeIn( 250 );
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

// eslint-disable-next-line no-unused-vars
function goToPage( page ) {
    const size = document.getElementById( "size__select" ).value;
    const col = document.getElementById( "sort__select" ).value;
    doPagination( page, size, col );
}

function clearForm() {
    document.getElementById( "subscriber_id__input" ).value = "";
    document.getElementById( "subscriber_email" ).value = "";
}