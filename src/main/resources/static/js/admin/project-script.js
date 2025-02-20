function editProject( id ) {
    window.location.href = `/admin/project/${ id }`;
}

function deleteProject( id ) {
    axios.delete( `/admin/project/delete/${ id }` );
}
