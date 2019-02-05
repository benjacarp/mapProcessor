package utn.frt.MapProcessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utn.frt.MapProcessor.exception.FileExtensionException;
import utn.frt.MapProcessor.exception.ValidationException;
import utn.frt.MapProcessor.model.Point;
import utn.frt.MapProcessor.service.MapService;

import javax.activation.FileTypeMap;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MapController {

    @Autowired
    private MapService mapService;

    @GetMapping("/prueba")
    public @ResponseBody
    String getText() {
        return "Hello world";
    }

    @PostMapping("/imageNumbers")
    public ResponseEntity<byte[]> imageNumbers(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("xs") int[] xs,
                                                   @RequestParam("ys") int[] ys) {
        try {

            String location;

            List<Point> points = getPoints(xs, ys);

            if (!file.getOriginalFilename().matches(".*\\.jpg"))
                throw new FileExtensionException("");

            location = mapService.process(file, points);
            File img = new File(location);
            return ResponseEntity.ok().contentType(MediaType.valueOf(
                    FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));

        } catch (ValidationException | FileExtensionException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    private List<Point> getPoints(@RequestParam("xs") int[] xs, @RequestParam("ys") int[] ys) throws ValidationException {
        List<Point> points = new ArrayList<>();

        if (xs.length != ys.length)
            throw new ValidationException("X array and Y array should be the same length");

        for (int i = 0; i < xs.length; i++) {
            points.add(new Point(xs[i], ys[i], null));
        }
        return points;
    }

    @PostMapping("/imageCircles")
    public ResponseEntity<byte[]> imageCircles(@RequestParam("file") MultipartFile file,
                                                   @RequestParam("xs") int[] xs,
                                                   @RequestParam("ys") int[] ys,
                                                   @RequestParam("colors") String[] colors ) {
        try {

            String location;

            List<Point> points = new ArrayList<>();

            for (int i = 0; i < xs.length; i++) {
                points.add(new Point(xs[i], ys[i], colors[i]));
            }

            if (!file.getOriginalFilename().matches(".*\\.jpg"))
                throw new FileExtensionException("");

            location = mapService.process(file, points);
            File img = new File(location);
            return ResponseEntity.ok().contentType(MediaType.valueOf(
                    FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));

        } catch (ValidationException | FileExtensionException e) {
            return ResponseEntity.badRequest().build();
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
