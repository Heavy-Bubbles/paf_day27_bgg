package sg.edu.nus.iss.paf_day27_bgg.repository;

import java.util.List;
import java.util.Optional;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class GameRepo {
    
    @Autowired
    private MongoTemplate mongoTemplate;

    /*
     * db.games.find({
     *      name: { $regex: 'a name', $options: 'i'}
     * })
     * .sort({ name : 1 })
     * .projection ({ id: 1, name: 1, _id: 0 })
     */
    public List<Document> findGameByName(String name){
        
        Criteria criteria = Criteria.where(Constants.A_NAME)
            .regex(name, "i");

        Query query = Query.query(criteria)
            .with(Sort.by(Direction.ASC, Constants.A_NAME));

        query.fields()
            .include(Constants.A_GID, Constants.A_NAME)
            .exclude(Constants.A_OBJECTID);

        return mongoTemplate.find(query, Document.class, Constants.C_GAMES);
    }

    /*
     * db.games.findOne({ gid: gid })
     */

    public Optional<Document> findGameByGid(Integer gid){

        Criteria criteria = Criteria.where(Constants.A_GID).is(gid);

        Query query = Query.query(criteria);

        List<Document> result = mongoTemplate.find(query, Document.class, Constants.C_GAMES);
        if (result.isEmpty()){
            return Optional.empty();
        }

        return Optional.of(result.get(0));
    } 
}
