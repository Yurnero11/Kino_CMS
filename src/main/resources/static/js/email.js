document.addEventListener('DOMContentLoaded', function() {
    // Получаем ссылку на кнопку "Send to All Users"
    var sendToAllButton = document.getElementById('sendToAll');

    // Добавляем обработчик события нажатия на кнопку
    sendToAllButton.addEventListener('click', function() {
        // Устанавливаем флаг "Сразу всем пользователям" в положение "включено"
        this.checked = true;
        // Отправляем форму
        document.querySelector('form').submit();
    });
});