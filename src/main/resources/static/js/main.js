const PREFIX_URL = "PortierDigital-SichniyA";

document.addEventListener( "DOMContentLoaded", function () {
    const linkBtn = document.getElementById( "link__btn" );
    linkBtn.addEventListener( "click", function () {
        fetch( `${ location.origin }/${ PREFIX_URL }/archs/portier-digital.zip` )
            .then( responce => {
                if ( responce.ok ) {
                    return responce.blob();
                } else {
                    throw new Error( "Error donloading template" );
                }
            } )
            .then( blob => {
                const link = document.createElement( "a" );
                const url = URL.createObjectURL( blob );
                link.href = url;
                link.download = "pg-templ.zip";
                link.click();
                link.remove();
                URL.revokeObjectURL( url );
            } )
            .catch( err => console.error( err.message ) );
    } );



} );

const iconMenu = document.querySelector( '.menu__icon' );
const body = document.body;
body.classList.remove( 'no-scroll' );
if ( iconMenu ) {
    const menuBody = document.querySelector( '.header__menu' );
    iconMenu.addEventListener( "click", function () {
        iconMenu.classList.toggle( 'active' );
        menuBody.classList.toggle( 'active' );

        // Добавляем/удаляем класс на body только при активированном меню
        if ( iconMenu.classList.contains( 'active' ) ) {
            body.classList.add( 'no-scroll' ); // Добавляем класс, если меню открыто
        } else {
            body.classList.remove( 'no-scroll' ); // Удаляем класс, если меню закрыто
        }
    } );
}

