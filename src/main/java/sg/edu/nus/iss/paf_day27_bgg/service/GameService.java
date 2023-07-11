package sg.edu.nus.iss.paf_day27_bgg.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_day27_bgg.model.Comment;
import sg.edu.nus.iss.paf_day27_bgg.model.Game;
import sg.edu.nus.iss.paf_day27_bgg.model.Utility;
import sg.edu.nus.iss.paf_day27_bgg.repository.CommentRepo;
import sg.edu.nus.iss.paf_day27_bgg.repository.GameRepo;

@Service
public class GameService {
    
    @Autowired
    private GameRepo gameRepo;

    @Autowired
    private CommentRepo commentRepo;

    public List<Game> searchGameByName(String name){

        return gameRepo.findGameByName(name).stream()
            .map(Utility :: toGameSummary)
            .toList();
    }

    public Optional<Game> findGameById(Integer gid){
        
        // find the game, if it does not exists, exit 
        Optional<Document> gameOpt = gameRepo.findGameByGid(gid);
        if(gameOpt.isEmpty()){
            return Optional.empty();
        }

        // get the comments, and add the comments to the game if any
        List<Document> commentDocs = commentRepo.findCommentsById(gid);
        return Optional.of(Utility.toGame(gameOpt.get(), commentDocs));
    }

    public void postComment(Comment comment){
        String cid = UUID.randomUUID().toString().substring(0, 8);
        comment.setCId(cid);

        ObjectId objectId = commentRepo.insertComment(comment);

        System.out.printf(">>> objectid: %s\n", objectId);
    }
}
