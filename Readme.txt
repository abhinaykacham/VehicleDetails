Project structure consists of four packages

1) com/example/AsyncTasks - This Package consists of 3 Async Tasks that are used to fetch data from REST Endpoints in the background, populate data to Spinners and RecyclerView
		
2) com/example/Helper - This package consists of Two classes
	HttpHandler - Used to fetch data from REST Endpoints and provide them as Response in the form of String to Async Objects
	Reference - This class consists of constants that are used throughout the project.
			
3) com/example/POJO - This package consists of Plain Java classes that hold details of Vehicle Maker, Vehicle Model, and Vehicle Information associated with each Model in List of given available vehicles of a selected model.
		
4) com/example/vehicledata - This package consists of Activities, Fragment, and an Adapter classes that help in rendering to UI.

Project Flow:
	Once the App launches, Carmakers are fetched from given REST API and populated in Spinner 1.
By default, Carmaker at index 0 chosen and Models of that maker are fetched and populated in Spinner 2.
Again, the Car model at index 0 chosen to populate the list of available cars that belonged to that model.
When the user chooses an Item from Spinners' respective models and list of cars of chosen make-model combinations are displayed.

	After selecting a car from the list, Make-Model, Price of car, Vehicle description, Image(If available and placeholder if Image is not available), and Last updated date are displayed.
The above implementations are available in both Mobile and Tablets with the facility to save the previous state when the user changes from Landscape and Portrait mode.

Contribution
We divided the work load as follows: 
1. Mobile UI and Vehicle Activity Development(Abhinay)
2. Tablet UI and Fragment Development(Dinesh)
3. GetModelDetails, GetCars Async Task developed by Abhinay
4. GetCarInformation Async Task developed by Dinesh
5. Saved Instance of activity (Abhinay)
6. CardView RecyclerView Design (Dinesh)

