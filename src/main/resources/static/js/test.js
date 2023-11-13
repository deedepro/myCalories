function onDragStart(event) {
    event
        .dataTransfer
        .setData('text/plain', event.target.id);

    event
        .currentTarget
        .style
        .backgroundColor = 'yellow';
}

function onDragOver(event) {
    event.preventDefault();
}

function onDrop(event) {
    const id = event
        .dataTransfer
        .getData('text');
    const draggableElement = document.getElementById(id);
    const dropzone = event.target;
    dropzone.appendChild(draggableElement);
    event
        .dataTransfer
        .clearData();
}

function addToDiary(event) {
    var id = event
        .dataTransfer
        .getData('text')
        .replace(/[^0-9]/g,"");
    var weight = $('#weight-' + id).val();
    $.get('/diary/testMethod', {weight: weight, id: id}, function(data){
        var result = data.text;
        var resultId = '#result-' + id;
        console.log(id);
        $(resultId).text("Продукт добавлен " + result);
        // alert("Продукт добавлен" + result);
    });
}