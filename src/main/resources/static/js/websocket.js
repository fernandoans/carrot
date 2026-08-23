const socket = new SockJS('/game');
const stompClient = Stomp.over(socket);

// Desativa logs excessivos do STOMP no console (opcional)
stompClient.debug = null;

stompClient.connect({}, () => {
    console.log("Conectado ao WebSocket do Jogo!");
    stompClient.subscribe('/topic/game', handleGameEvent);
});

function handleGameEvent(message) {
    const event = JSON.parse(message.body);

    // Verifica se é celular ou telão através de um atributo na div container
    const heroCard = document.getElementById('hero-card');
    if (!heroCard) return;

    const isPlayer = heroCard.dataset.view === 'player';
    let fragmentUrl = '';

    switch (event.type) {
        case "GAME_WAITING":
            fragmentUrl = isPlayer ? '/frg/player/join' : '/frg/screen/lobby';
            break;
        case "QUESTION_STARTED":
            fragmentUrl = isPlayer ? '/frg/player/question' : '/frg/screen/question';
            break;
        case "SHOW_RANKING":
            fragmentUrl = isPlayer ? '/frg/player/ranking' : '/frg/screen/ranking';
            break;
        case "GAME_FINISHED":
            fragmentUrl = isPlayer ? '/frg/player/ranking' : '/frg/screen/finished';
            break;
    }

    if (fragmentUrl) {
        htmx.ajax('GET', fragmentUrl, {
            target: '#hero-card',
            swap: 'innerHTML'
        });
    }
}