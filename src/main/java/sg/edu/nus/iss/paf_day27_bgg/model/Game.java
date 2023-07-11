package sg.edu.nus.iss.paf_day27_bgg.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {

    private Integer gid;
    private String name;
    private Integer year;
    private String url;
    private String image;
    private List<Comment> comments;
    
}
