public class TedTalk implements Comparable <TedTalk> {
    private String title;
    private String author;
    private String date;
    private int views;
    private int likes;
    private String link;

    // default constructor
    public TedTalk() {
    }

    // constructor
    public TedTalk(String title, String author, String date, int views, int likes, String link) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.views = views;
        this.likes = likes;
        this.link = link;
    }

    // String interface for TedTalk
    @Override
    public String toString() {
        String tedTalkInfo;
        tedTalkInfo = views + " views: \"" + title + "\" by " + author + " (" + date + ")";
        return tedTalkInfo;
    }

    // Comparable interface for number of views of TedTalks
    @Override
    public int compareTo(TedTalk talk2) {
        return Integer.compare(this.getViews(), talk2.getViews());
    }

    // getter for title
    public String getTitle() {
        return title;
    }

    // getter for author
    public String getAuthor () {
        return author;
    }

    // getter for date
    public String getDate() {
        return date;
    }

    // getter for number of views
    public int getViews() {
        return views;
    }

    // getter for number of likes
    public int getLikes() {
        return likes;
    }

    // getter for link
    public String getLink() {
        return link;
    }

}
