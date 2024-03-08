package ENTITY;

public class Dummy {
    private String title;
    private int authorId;
    private int genreId;
    private String publicationDate;
    private String damaged; 
    private String repairStatus;

    
     
    public String getDamaged() {
        return damaged;
    }
    public void setDamaged(String damaged) {
        this.damaged = damaged;
    }
    public String getRepairStatus() {
        return repairStatus;
    }
    public void setRepairStatus(String repairStatus) {
        this.repairStatus = repairStatus;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getAuthorId() {
        return authorId;
    }
    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }
    public int getGenreId() {
        return genreId;
    }
    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }
    public String getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
  
    public Dummy(String title, int authorId, int genreId, String publicationDate, String damaged, String repairStatus) {
        this.title = title;
        this.authorId = authorId;
        this.genreId = genreId;
        this.publicationDate = publicationDate;
        this.damaged = damaged;
        this.repairStatus = repairStatus;
    }
    public Dummy(int bookId, String bookName, String publishDate, int authorId2, int genId, String damage) {
    }
    
}
