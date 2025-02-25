/* global document axios location console $ FormData Event*/

document.addEventListener( "DOMContentLoaded", function () {
    addListenerToEditBtns();
    addListenerToDeleteBtns();
    addListenerToCloseBtns();
    addListenerToSaveBtn();
    addListenerToSortSelect();
} );

async function addListenerToEditBtns() {
    const editBtns = document.querySelectorAll( "[id^='edit__btn'" );
    for ( const btn of editBtns ) {
        btn.addEventListener( "click", function () {
            const rowElems = btn.closest( "tr" ).children;
            document.getElementById( "skill_id__input" ).value = rowElems[ 0 ].textContent;
            document.getElementById( "skill_title" ).value = rowElems[ 1 ].textContent;
            document.getElementById( "skill_description" ).value = rowElems[ 2 ].textContent;
        } );
    }
}

async function addListenerToDeleteBtns() {
    const delBtns = document.querySelectorAll( "[id^='delete__btn']" );
    for ( const btn of delBtns ) {
        btn.addEventListener( "click", function () {
            const row = btn.closest( "tr" );
            const id = row.children[ 0 ].textContent;
            axios.delete( `${ location.href }/delete/${ id }` )
                .then( function ( responce ) {
                    console.log( responce.statusText );
                    $( row ).fadeOut( 250, ( row ) => {
                        $( row ).remove();
                    } );
                } )
                .catch( error => console.error( error.message ) );
        } );
    };
}

async function addListenerToCloseBtns() {
    document.getElementById( "up_close_modal__btn" ).addEventListener( "click", clearFormInModal );
    document.getElementById( "close_modal__btn" ).addEventListener( "click", clearFormInModal );
}

async function addListenerToSaveBtn() {
    const saveBtn = document.getElementById( "save_changes__btn" );
    saveBtn.addEventListener( "click", function () {
        const form = document.getElementById( "project__form" );
        const formData = new FormData( form );
        let isNew = !formData.get( "id" );
        axios.post( `${ location.href }/save`, formData )
            .then( response => {
                console.log( response.statusText );
                const obj = response.data;
                const tableBody = document.querySelector( "table>tbody" );
                if ( isNew ) {
                    const cloneRow = document.querySelector( "table>tbody>tr" ).cloneNode( true );
                    cloneRow.children[ 0 ].textContent = obj.id;
                    cloneRow.children[ 1 ].textContent = obj.title;
                    cloneRow.children[ 2 ].textContent = obj.description;
                    tableBody.append( cloneRow );
                } else {
                    for ( const row of tableBody.children ) {
                        if ( row.children[ 0 ].textContent === String( obj.id ) ) {
                            row.children[ 1 ].textContent = obj.text;
                            row.children[ 2 ].textContent = obj.description;
                            break;
                        }
                    }
                }
                addListenerToEditBtns();
                addListenerToDeleteBtns();
                document.getElementById( "close_modal__btn" ).dispatchEvent( new Event( "click" ) );
            } )
            .catch( err => console.error( err.message ) );

    } );
}

async function addListenerToSortSelect() {
    document.getElementById( "sort__select" ).addEventListener( "change", function ( event ) {
        const sortType = event.target.value;
        axios.get( `${ location.href }/sort?sort=${ sortType }` )
            .then( function ( responce ) {
                const pageEl = responce.data;
                document.getElementById( "skills_table__section" ).innerHTML = pageEl;
                addListenerToEditBtns();
                addListenerToDeleteBtns();
            } )
            .catch( err => console.error( err.message ) );
    } );
}

const clearFormInModal = function () {
    document.getElementById( "skill_id__input" ).value = "";
    document.getElementById( "skill_title" ).value = "";
    document.getElementById( "skill_description" ).value = "";
};