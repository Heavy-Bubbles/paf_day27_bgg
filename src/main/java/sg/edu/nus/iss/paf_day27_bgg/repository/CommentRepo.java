package sg.edu.nus.iss.paf_day27_bgg.repository;

import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_day27_bgg.model.Comment;

@Repository
public class CommentRepo {

    @Autowired
    private MongoTemplate mongotemplate;

    /*
     * db.comments.find({ gid: gid })
     *      .sort({ rating: -1 })
     *      .limit(5)
     */
    public List<Document> findCommentsById(Integer gid){
        Criteria criteria = Criteria.where(Constants.A_GID).is(gid);

        Query query = Query.query(criteria)
            .with(Sort.by(Direction.DESC, Constants.A_RATING))
            .limit(5);

        return mongotemplate.find(query, Document.class, Constants.C_COMMENTS);
    }

    /*
     * db.comments.insert({ ... })
     */
     public ObjectId insertComment(Comment comment){
        Document doc = new Document();
        doc.put(Constants.A_CID, comment.getCId());
        doc.put(Constants.A_USER, comment.getUser());
        doc.put(Constants.A_RATING, comment.getRating());
        doc.put(Constants.A_TEXT, comment.getText());
        doc.put(Constants.A_GID, comment.getGId());

        Document newDoc = mongotemplate.insert(doc, Constants.C_COMMENTS);
        return newDoc.getObjectId(Constants.A_ID);
     }
}
