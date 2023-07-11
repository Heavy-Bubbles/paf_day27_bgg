package sg.edu.nus.iss.paf_day27_bgg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import sg.edu.nus.iss.paf_day27_bgg.model.Comment;
import sg.edu.nus.iss.paf_day27_bgg.model.Game;
import sg.edu.nus.iss.paf_day27_bgg.service.GameService;

@Controller
@RequestMapping
public class GameController {
    
    @Autowired
    private GameService gameService;

    @GetMapping(path = "/search")
    public ModelAndView search(@RequestParam String name){

        List<Game> games = gameService.searchGameByName(name);

        ModelAndView mav = new ModelAndView();
        mav.setViewName("search-result");
        mav.addObject("game", name);
        mav.addObject("games", games);
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

    @GetMapping("/game/{gid}")
    public ModelAndView getGameByGid(@PathVariable Integer gid){

        Optional<Game> opt = gameService.findGameById(gid);

        ModelAndView mav = new ModelAndView();
        mav.addObject("gid", gid);

        if (opt.isEmpty()){
            mav.setViewName("not-found");
            mav.setStatus(HttpStatusCode.valueOf(404));
            return mav;
        }

        mav.setViewName("game-detail");
        mav.addObject("game", opt.get());
        mav.setStatus(HttpStatusCode.valueOf(200));

        return mav;
    }

    @PostMapping(path = "/comment")
    public ModelAndView comment(@RequestBody MultiValueMap<String, String> form){
        
        Integer gid = Integer.parseInt(form.getFirst("gid"));
        String user = form.getFirst("user");
        Integer rating = Integer.parseInt(form.getFirst("rating"));
        String text = form.getFirst("text");

        Comment comment = new Comment("", user, rating, text, gid);
        gameService.postComment(comment);

        return getGameByGid(gid);
    }
}
