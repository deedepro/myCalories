/**
 * Обработка запроса на добавление записи в дневник
 * @param id food id
 */
function addToDiary(id) {
    event.preventDefault();
    var mealtime = getMealtime();
    var date = $('#date').val();
    var weight = $('#weight').val();
    $.post('/diary/dropToDiary', {weight: weight, date: date, id: id, mealtime: mealtime}, function (data) {
        $('.modal').removeClass('modal_active');
    })
}

/**
 * Добавление продукта из модального окна
 */
function buttonToDiary() {
    var id = $('#select-food-id').val();
    addToDiary(id);
}

/**
 * Получение выбранного приема пищи
 * @returns строковое значение приема пищи
 */
function getMealtime() {
    var radios = $("input[name='mealtime']");
    var selected = radios.filter(":checked");
    return selected.val();
}

/**
 * Обновление страницы с учетом выбранных параметров даты и приема пищи
 */
function updatePageWithParams() {
    var mealtime = getMealtime();
    var date = $('#date').val();
    $("input[name='select-date']").val(date);
    $("input[name='select-mealtime']").val(mealtime);
    $('#hidden-form').submit();
}

/**
 * Показать всплывающее окно добавления записи в дневник
 */
function showAddDiaryModal(el) {
    var id = this.name.replace(/[^0-9]/g, "");
    setFoodIdToModal(id);
    $('.modal').addClass('modal_active');
}

/**
 * Скрыть всплывающее окно
 */
function hideAddDiaryModel() {
    $('.modal').removeClass('modal_active');
}

function setFoodIdToModal(id) {
    $('#select-food-id').val(id);
}

function showEditDiaryModal(el) {
    var id = el.name.replace(/[^0-9]/g, "");
    setFoodIdToModal(id);
    $('#modal-submit-button').val('Изменить');
    showAddDiaryModal();
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