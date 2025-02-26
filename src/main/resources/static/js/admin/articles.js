/*global document console alert location axios $ confirm event Event DOMParser FormData FileReader*/
const EXT = "jpg";
const PREFIX_URL = "/PortierDigital-SichniyA";


document.addEventListener( "DOMContentLoaded", function () {
    addListenersToEditBtns();
    addListenersToDeleteBtns();
    addListenerToSaveFormBtn();
    addListenerToCloseModalBtns();
    addListenerToImageInput();
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
            axios.get( `${ location.href }/${ id }` )
                .then( response => {
                    const obj = JSON.parse( JSON.stringify( response.data ) );
                    $( "#article__form" )
                        .find( "#article_id__input" ).val( obj.id ).end()
                        .find( "#article_title" ).val( obj.title ).end()
                        .find( "#article_content" ).val( obj.content );
                    if ( obj.imagePath ) {
                        $( "form #article__img" ).attr( "src", `${ PREFIX_URL }/${ obj.imagePath }.${ EXT }` );
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
        const form = document.getElementById( "article__form" );
        const formData = new FormData( form );
        const file = document.getElementById( "article_image__input" ).files[ 0 ];
        let imagePath = file ?
            await saveImageOnServer( file, formData.get( "title" ) ) :
            document.getElementById( "article__img" ).src;
        formData.append( "imagePath", imagePath );

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

async function addListenerToImageInput() {
    const imgInput = document.getElementById( "article_image__input" );
    imgInput.addEventListener( "change", function () {
        const file = this.files[ 0 ];
        if ( file ) {
            const fileReader = new FileReader();
            fileReader.onload = function ( event ) {
                document.getElementById( "article__img" ).src = event.target.result;
            };
            fileReader.readAsDataURL( file );
        }
    } );
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

async function saveImageOnServer( file, imgName ) {
    const formData = new FormData();
    formData.append( "file", file, imgName );
    formData.append( "timestamp", new Date().getTime().toString() );
    formData.append( "ext", EXT );

    try {
        const responce = await axios.post( `${ location.href }/image/save`, formData );
        console.log( responce.data );
        return responce.data;
    } catch ( error ) {
        alert( "Помилка збереження картинки" + error.message );
        return null;
    }
}

// eslint-disable-next-line no-unused-vars
function goToPage( page ) {
    const size = document.getElementById( "size__select" ).value;
    const col = document.getElementById( "sort__select" ).value;
    doPagination( page, size, col );
}

function clearForm() {
    document.getElementById( "article_id__input" ).value = "";
    document.getElementById( "article__img" ).src = `${ PREFIX_URL }/images/article_stub.webp`;
    document.getElementById( "article_image__input" ).files[ 0 ] = null;
    document.getElementById( "article_image__input" ).value = "";
    document.getElementById( "article_title" ).value = "";
    document.getElementById( "article_content" ).value = "";
}