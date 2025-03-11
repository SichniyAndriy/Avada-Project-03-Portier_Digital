

document.addEventListener( "DOMContentLoaded", function () {
    addEventListenerTuSubscribeBtn();
    addEventListenerToOpenModalBtn();
    addEventListenerToCloseModalBtn();
} );

async function addEventListenerTuSubscribeBtn() {
    document.getElementById( "subcribe__btn" ).addEventListener( "click", function () {
        const emailInput = document.getElementById( "email" );
        const email = emailInput.value;
        if ( !email.match( "^[a-zA-Z_]+[\w\d]*@[a-zA-Z]{3,12}\.[a-zA-Z]{2,4}$" ) ) {
            alert( "Введіть валідну пошту" );
            return;
        }
        const formData = new FormData();
        formData.append( "email", email );
        fetch( `${ location.origin }/PortierDigital-SichniyA/admin/subscribers/save`, {
            method: "POST",
            body: formData
        } )
            .then( responce => {
                if ( responce.ok ) {
                    return responce.text();
                } else {
                    throw Error( "Помилка" );
                }
            } )
            .then( text => {
                alert( `Новий підписник. ID: ${ text }` );
                emailInput.value = "";
            } )
            .catch( err => alert( err.message ) );
    } );
}

async function addEventListenerToOpenModalBtn() {
    const articleHeaderEl = document.getElementById( "article_header__modal" );
    const articleTextEl = document.getElementById( "article_text__modal" );
    const openBtns = document.querySelectorAll( "div.articles__items div>.btn" );
    for ( const btn of openBtns ) {
        btn.addEventListener( "click", function () {
            articleHeaderEl.innerText = btn.parentElement.previousElementSibling.previousElementSibling.innerText;
            articleTextEl.innerText = btn.parentElement.previousElementSibling.innerText;
        } );
    }
}

async function addEventListenerToCloseModalBtn() {
    const articleHeaderEl = document.getElementById( "article_header__modal" );
    const articleTextEl = document.getElementById( "article_text__modal" );
    const openBtns = document.querySelectorAll( "#project_page__modal button" );
    for ( const btn of openBtns ) {
        btn.addEventListener( "click", function () {
            const articleHeaderEl = "";
            articleTextEl.innerText = "";
        } );
    }
}