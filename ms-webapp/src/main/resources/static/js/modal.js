document.addEventListener('DOMContentLoaded', function () {
    const shouldOpen = [[${openModal} ?: false]];
    console.log(">> openModal =", shouldOpen); // 💡 à vérifier dans la console navigateur
    if (shouldOpen) {
        const modalElement = document.getElementById('editNoteModal');
        const modalInstance = new mdb.Modal(modalElement);
        modalInstance.show();
    }
});