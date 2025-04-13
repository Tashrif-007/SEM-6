package com.example.mavennServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.example.mavenn.HashUtil;

@RestController
public class StringController {
    @GetMapping("/{str}")
    public String hash(@PathVariable String str) {
        return HashUtil.hash(str);
    }
}
