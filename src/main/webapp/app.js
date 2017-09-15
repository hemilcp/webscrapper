
var app = angular.module('PhilosophyWiki',[]);

app.controller('MainCtrl',[
	'$scope','$http',
	function($scope, $http){

		$scope.test = 'Hello world!';
		$scope.hopshide = true;

		// var randomColor = "background: #" + Math.floor(Math.random()*16777215).toString(16);

		var colorCodeArray = [
			"#339E42",
			"#009688",
			"#039BE5",
			"#EF6C00",
			"#607D8B",
			"#039BE5",
			"#A1887F",

			];

		$scope.getRandomColor = function($index){
			return "display: inline-block; margin: 10px; clear: both; background: "+colorCodeArray[$index % colorCodeArray.length];
		}

		// $scope.getRandomColor = function($index){
		// 	return "background: #" + Math.floor(Math.random()*16777215).toString(16);
		// }

		$scope.findPhilosophy = function(){
			console.log($scope.wikiLink);
			var data = {
					link: $scope.wikiLink
			};

			var config = {
					headers : {
						'Content-Type': 'application/json;charset=utf-8;',
						'Access-Control-Allow-Origin': '*',
						'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
					}
			}
//			console.log("Inside");

			$scope.loading = true;
			$scope.postdata = true;
			$scope.hopshide = true;

			$http.post('http://localhost:8080/webscrapper/webapi/webscrapper', data, config)
			.success(function (data, status, headers, config) {
				$scope.PostDataResponse = data;
				$scope.hops = data.length;
			})
			.error(function (data, status, header, config) {
				$scope.ResponseDetails = "Data: " + data +
				"<hr />status: " + status +
				"<hr />headers: " + header +
				"<hr />config: " + config;
			})
			.finally(function(){
				$scope.loading = false;
				$scope.postdata = false;
				$scope.hopshide = false;
			})
		};

	}]);