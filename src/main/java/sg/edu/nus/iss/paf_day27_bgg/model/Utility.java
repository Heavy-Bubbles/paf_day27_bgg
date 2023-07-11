package sg.edu.nus.iss.paf_day27_bgg.model;

import java.util.List;

import org.bson.Document;

import sg.edu.nus.iss.paf_day27_bgg.repository.Constants;

public class Utility {

    public static Game toGameSummary(Document doc){
        return new Game(
            doc.getInteger(Constants.A_GID),
            doc.getString(Constants.A_NAME),
            0, "", "", null);
    }

    public static Game toGame(Document doc, List<Document> comments){
        return new Game(
            doc.getInteger(Constants.A_GID),
            doc.getString(Constants.A_NAME),
            doc.getInteger(Constants.A_YEAR),
            doc.getString(Constants.A_URL),
            doc.getString(Constants.A_IMAGE),
            comments.stream().map(Utility :: toComment).toList()
        );
    }

    public static Comment toComment(Document doc){
        return new Comment(
            doc.getString(Constants.A_CID),
            doc.getString(Constants.A_USER),
            doc.getInteger(Constants.A_RATING),
            doc.getString(Constants.A_TEXT),
            doc.getInteger(Constants.A_GID)
        );
    }
    
}
