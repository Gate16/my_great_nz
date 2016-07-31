(function() {
    'use strict';

    angular.module('myGreatNzApp')
        .controller('ResultPageWatsonController', ResultPageWatsonController);

    ResultPageWatsonController.$inject = ['$scope', '$state'];

//    var audio = document.getElementById('audio');
//    var wavsource = document.getElementById('wavsource');

    function ResultPageWatsonController () {
        var text ="Result Page Watson Controller"
        wavsource.src = '/api/public/speak?text=' + text;

//        audio.load();
//        audio.play();
    }

    ResultPageWatsonController();

})();
