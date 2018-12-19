
/**
 * This class inherits from BST and is used to store all the Movie objects.
 * @author atara
 *
 */
public class MovieList extends BST<Movie> {

	/**
	 * Constructs a new MovieList by calling the constructor of it's superclass, LinkedList
	 */
	public MovieList() {
		super();
	}
	
	/**
	 * Adds new movie to the list. Void return.
	 * @param m the movie to be added
	 */
	public void addMovie(Movie m) {
		if (this.contains(m)) {
			get(m).addLocation(m.getSfLocations().get(0)); //so just add the location
			return;
		}
		else {
			add(m);
		}
	}
	
	/**
	 * This method should return a list of Movie objects whose titles contain the specified keyword as a substring.
	 * @param title the specified keyword to search for
	 * @return a MovieList called matchingTitles
	 */
	public MovieList getMatchingTitles(String title) {
		MovieList matchingTitles = new MovieList();
		if (title == null || title.trim().length() == 0) {
			return null;
		}
		for (Movie m : this) {
			String theTitle = get(m).getTitle().trim();
			if (theTitle.toLowerCase().contains(title.trim().toLowerCase())) {
				matchingTitles.add(get(m));
			}
		}
		if (matchingTitles.size() == 0) {
			return null;
		}
		return matchingTitles;
	}

	/**
	 * This method should return a list of Movie object whose actors names contain the keyword as a substring.
	 * @param actor the specified keyword to search for
	 * @return MovieList - the type of list to be returned - called matchingActors 
	 */	
	public MovieList getMatchingActor(String actor) {
		MovieList matchingActors = new MovieList();
		if (actor == null || actor.trim().length() == 0) {
			return null;
		}
		for (Movie m : this) {
			if (get(m).getActor1().getName().trim().toLowerCase().contains(actor.trim().toLowerCase())) {
				matchingActors.add(get(m));
			}
			else {
				Actor actor2 = (get(m)).getActor2();
				if (actor2 != null && actor2.getName().trim().toLowerCase().contains(actor.trim().toLowerCase())) {
					matchingActors.add(get(m));
				}
				else {
					Actor actor3 = (get(m)).getActor3();
					if (actor3 != null && actor3.getName().trim().toLowerCase().contains(actor.trim().toLowerCase())) {
						matchingActors.add(get(m));
					}
				}
			}
		}
		if (matchingActors.isEmpty()) {
			return null;
		}
		return matchingActors;
	}
}

	