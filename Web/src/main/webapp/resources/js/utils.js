$(document).ready(function(){
  
    $('#topicAdd').click(function () {
        addTopic();
    });
});

function addTopic() {
    var name = $("#name").val();
    var description = $("#description").val();

    var topic = {
        name: name,
        description: description,
    };
    $.ajax({
        data:topic,
        dataType: "json",
        method:"POST",
        url: "../topics/add"
    }).done(function(data) {

    }).fail(function(data){
        if ( console && console.log ) {
            console.log( "Error data:", data.slice( 0, 100 ) );
        }
    });
}

