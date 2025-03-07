

document.addEventListener( "DOMContentLoaded", function () {
    this.getElementById( "subcribe__btn" ).addEventListener( "click", function () {
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
} );
