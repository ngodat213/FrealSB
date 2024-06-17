package com.example.frealsb.RequestEntities;

import com.example.frealsb.Entities.FoodFeatured;
import com.example.frealsb.Entities.Location;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class RequestFoodFeature {
    private String id;
    private String title;
    private String description;
    private Location location;

    public FoodFeatured toAddData(){
        FoodFeatured data = new FoodFeatured();
        data.setTitle(title);
        data.setDescription(description);
        data.setLocation(location);
        return data;
    }

    public FoodFeatured toUpdateData(){
        FoodFeatured data = new FoodFeatured();
        data.setId(id);
        data.setTitle(title);
        data.setDescription(description);
        data.setLocation(location);
        return data;
    }
}
