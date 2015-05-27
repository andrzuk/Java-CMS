package checkers;

import java.util.ArrayList;

public class Punctuation {

	private ArrayList<Pair> pairs = new ArrayList<Pair>();
	
	public Punctuation() {
	
		pairs.clear();
		
		pairs.add(new Pair(" .", "."));
		pairs.add(new Pair(" ,", ","));

		pairs.add(new Pair(".", ". "));
		pairs.add(new Pair(",", ", "));
		
		pairs.add(new Pair("  ", " "));
	}
	
	private class Pair {
		
		private String from;
		private String to;
		
		public Pair(String from, String to) {
		
			this.from = from;
			this.to = to;
		}
		
		public String getFrom() {
			
			return this.from;
		}
		
		public String getTo() {
			
			return this.to;
		}
	}
	
	public String formatContent(String content) {
		
		for (int i = 0; i < this.pairs.size(); i++) {
			
			content = content.replace(this.pairs.get(i).getFrom(), this.pairs.get(i).getTo());
		}
		return content;
	}
}
