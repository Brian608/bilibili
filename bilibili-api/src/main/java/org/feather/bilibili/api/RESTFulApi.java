package org.feather.bilibili.api;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: bilibili
 * @description:
 * @author: 杜雪松(feather)
 * @since: 2022-04-10 19:11
 **/
@RestController
public class RESTFulApi {
    private final Map<Integer, Map<String, Object>> dataMap;

    public RESTFulApi(){
        dataMap=new HashMap<>();
        for (int i = 1; i < 3; i++) {
        Map<String, Object> data=new HashMap<>();
        data.put("id",i);
        data.put("name","name"+i);
        dataMap.put(i,data);
        }
    }

    @GetMapping("/objects/{id}")
    public Map<String, Object> getData(@PathVariable  Integer id){
        return  dataMap.get(id);
    }

    @DeleteMapping("/objects/{id}")
    public  String deleteData(@PathVariable Integer id){
        dataMap.remove(id);
        return "delete success!";
    }

    @PostMapping("/objects")
    public String postData(@RequestBody  Map<String, Object> data){
        Integer[] idArray = dataMap.keySet().toArray(new Integer[0]);
        Arrays.sort(idArray);
        int nextId=idArray[idArray.length-1]+1;
        dataMap.put(nextId,data);
        return  "post success!";
    }
    @PutMapping("/objects")
    public String putData(@RequestBody  Map<String, Object> data){
        Integer id = Integer.valueOf(String.valueOf(data.get("id")));
        Map<String, Object> containerMap = dataMap.get(id);
        if (containerMap==null){
            //添加
            Integer[] idArray = dataMap.keySet().toArray(new Integer[0]);
            Arrays.sort(idArray);
            int nextId=idArray[idArray.length-1]+1;
            dataMap.put(nextId,data);
        }else {
            dataMap.put(id, data);
        }
        return "put success!";
    }
}
