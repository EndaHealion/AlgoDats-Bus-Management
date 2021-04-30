# AlgoDats-Bus-Management
Repo for college project

## External Libraries
JavaFX11 - https://openjfx.io/openjfx-docs/#install-java

## Group members
* [Enda Healion](https://github.com/EndaHealion)
* [Cian Mawhinney](https://github.com/cianmawhinney)
* [Cillian Fogarty](https://github.com/cillfog1)
* [Cian O'Gorman](https://github.com/CianOG123)


## Contribution
/   | Enda Healion | Cian Mawhinney | Cillian Fogarty | Cian O'Gorman
--|--|--|--|--
**Part 1** | | 100% | | |
**Part 2** | 100% | | | |
**Part 3** | | | 100% | |
**Part 4** |  | | | 100% |
**Demo Video** |  | | | 100% |
**Design Doc** | 33.3% | 33.3% | 33.3% | |

### Enda Healion:
* Implemented the ability to search for a bus stop by full name or by the first few characters in the name, using a ternary search tree and returning the full stop information for each stop matching the search criteria.
* Created the Stops class to make it easier to handle Stop information.
* Wrote about the algorithms implemented in the design document.

### Cian Mawhhinney
* Pending

### Cillian Fogarty
* Implemented the ability to search all valid trips from stop_times.txt, by a user inputted arrival_time, returning full details of all trips matching the criteria (zero, one or more), sorted by trip id.
* Created the ArrivalTimeSearch class which holds the implementation detailed above, by extracting the valid trips into an ArrayList and sorting by arrival_time and then by trip_id when the program begins. Segments are then extracted from this ArrayList and outputted to the user when they enter an arrival_time to search for.
* Wrote about the data structures I used and described my algorithm in the design document.

### Cian O'Gorman
* Responsible for designing and implementing the User Interface. We decided as a group it would be best to use a JavaFX user interface as I had previous experience using this library. We also decided that we would try and keep all of our functions static so that they could easily be accessed by the UI classes without the need to create object instances. This helped to make the code more memory efficient. Some of the ways that I kept the UI code clean and readable was by using lambda expressions to pass functions as parameters, this can be seen in the file DisplayPage.java. The alternative to using lambda expressions would be to copy and paste the button initialisation code which can look messy.
* As well as creating the UI, I also did some error handling. This error handling involving verifying the users input to ensure that it was valid and could be accepted by the algorithm methods that the other team members created.
