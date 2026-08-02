const socket = new SockJS('/game');
const stompClient = Stomp.over(socket);

stompClient.connect({}, () => {
  stompClient.subscribe(
      '/topic/game',
      handleGameEvent
  );
});

function handleGameEvent(message) {
    const event = JSON.parse(message.body);
    switch (event.type) {
        case "GAME_WAITING":
            htmx.ajax(
                'GET',
                '/fragments/lobby',
                { target: '#hero-card', swap: 'innerHTML' }
            );
            break;
        case "QUESTION_STARTED":
            htmx.ajax(
                'GET',
                '/fragments/question',
                { target: '#hero-card', swap: 'innerHTML' }
            );
            break;
        case "SHOW_RANKING":
            htmx.ajax(
                'GET',
                '/fragments/ranking',
                { target: '#hero-card', swap: 'innerHTML' }
            );
            break;
        case "GAME_FINISHED":
            htmx.ajax(
                'GET',
                '/fragments/finished',
                { target: '#hero-card', swap: 'innerHTML' }
            );
            break;
    }
}