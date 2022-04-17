package rocks.j5.uga.expanse.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "book_language")
public class BookLanguage {
    @Id
    @Column(name = "language_id", nullable = false)
    private Integer id;

    @Column(name = "language_code", length = 8)
    private String languageCode;

    @Column(name = "language_name", length = 50)
    private String languageName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }

}