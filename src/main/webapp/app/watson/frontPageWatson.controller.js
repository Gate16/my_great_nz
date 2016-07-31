(function() {
    'use strict';

    angular.module('myGreatNzApp')
        .controller('FrontPageWatsonController', FrontPageWatsonController);

    FrontPageWatsonController.$inject = ['$scope', 'Principal', 'LoginService', '$state'];

//    var audio = document.getElementById('audio');
//    var wavsource = document.getElementById('wavsource');

    function FrontPageWatsonController ($scope, Principal, LoginService, $state) {
//        var text ="Front Page Watson Controller"
//        wavsource.src = '/api/public/speak?text=' + text;
//
////        audio.load();
////        audio.play();

        $scope.audio = document.getElementById('audio');
        $scope.wavsource = document.getElementById('waveSourceFrontPage');
        $scope.text = "This is message is for front page"

        $scope.init = function(text) {
            $scope.wavsource.src = '/api/public/speak?text=' + text;

            $scope.audio.load();
            $scope.audio.play();
         }

        $scope.onClick = function () {
            console.log("Correct");

            $scope.wavsource.src = '/api/public/speak?text=Hardcoded';

            $scope.audio.load();
            $scope.audio.play();
        };

        //$scope.init("Called in constructor");
        $scope.init($scope.text);
    }

    function test() {
            console.log("Correct");

            $scope.wavsource.src = '/api/public/speak?text=Hardcoded';

            $scope.audio.load();
            $scope.audio.play();
    };

//    FrontPageWatsonController();

})();
