
var app = angular.module('PhilosophyWiki',[]);

app.controller('MainCtrl',[
	'$scope','$http',
	function($scope, $http){

		$scope.test = 'Hello world!';

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

			$http.post('http://localhost:8080/webscrapper/webapi/webscrapper', data, config)
			.success(function (data, status, headers, config) {
				$scope.PostDataResponse = data;
				console.log(data);
			})
			.error(function (data, status, header, config) {
				$scope.ResponseDetails = "Data: " + data +
				"<hr />status: " + status +
				"<hr />headers: " + header +
				"<hr />config: " + config;
			});
		};

	}]);