/**
 * Показать всплывающее окно добавления записи в дневник
 */
function showAddDiaryModal(id) {
    $('.modal').addClass('modal_active');
    $('#id-input-id').val(id);
}

/**
 * Скрыть всплывающее окно
 */
function hideAddDiaryModel() {
    $('.modal').removeClass('modal_active');
}

/**
 * Скрыть всплывающее окно при клике вне области
 */
$(function () {
    $('.modal').mouseup(function (e) {
        let modalContent = $(".modal__content");
        if (!modalContent.is(e.target) && modalContent.has(e.target).length === 0) {
            $(this).removeClass('modal_active');
        }
    });
});