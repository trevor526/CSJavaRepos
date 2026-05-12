public class Book {
	
		private int book_age;
		private String book_title;
		private String book_author; 
		private boolean filled;

		public Book() {
			filled = false;
		}
		
		public Book (int book_age, String book_title, String book_author) {
			this.book_age = book_age;
			this.book_title = book_title;
			this.book_author = book_author;
			
			filled = false;
			
		}
		public void printBook () {
			System.out.println ("Age: " + book_age);
			System.out.println ("Title: " + book_title);
			System.out.println ("Author: " + book_author);
		}
		public int getBookAge() {
			return book_age;
		}
		public void setbook_age (int book_age) {
			if (book_age >= 0) {
				this.book_age = book_age;
			} else {
					System.out.println("The book age cannot be less than 1 year old.");
				}
		}
		public String getBookTitle() {
			return book_title;
		}
		public void setbook_title(String book_title) {
	        this.book_title = book_title;
	    }
		public String getBookAuthor() {
			return book_author;
		}
		public void setbook_author(String book_author) {
	        this.book_author = book_author;
	    }
		@Override
		public String toString() {
			return book_title + " by " + book_author + " (" + book_age + ")";
		}

		}


