package com.appv1.demo.Controller;

import com.appv1.demo.Entity.User;
import com.appv1.demo.Entity.Vehicle;
import com.appv1.demo.Entity.VehicleInspection;
import com.appv1.demo.Service.UserService;
import com.appv1.demo.Service.VehicleInspectionService;
import com.appv1.demo.Service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
public class VehicleInspectionController {

    VehicleInspectionService vehicleInspectionService;
    VehicleService vehicleService;
    UserService userService;

    @Autowired
    public VehicleInspectionController(VehicleInspectionService vehicleInspectionService, VehicleService vehicleService, UserService userService) {
        this.vehicleInspectionService = vehicleInspectionService;
        this.vehicleService = vehicleService;
        this.userService = userService;
    }

    @GetMapping("/addInspection/{id}")
    public String showCreateVehicleInspectionForm(@PathVariable int id, Model model){
        model.addAttribute("vehicleInspection", new VehicleInspection());
        model.addAttribute("vehicle", vehicleService.getById(id));
        return "addInspection";
    }

    @PostMapping("/addInspection/{id}")
    public String createVehicleInspection(@PathVariable int id, @ModelAttribute VehicleInspection vehicleInspection){
        vehicleInspectionService.save(vehicleInspection);
        return "redirect:/vehicleInspections/{id}";
    }

    @GetMapping("/vehicleInspections/{id}")
    public String showVehicleInspections(@PathVariable int id, Model model){
        Vehicle vehicle = vehicleService.getById(id);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("inspections", vehicleInspectionService.findVehicleInspections(vehicle));
        return "vehicleInspections";
    }

    @GetMapping("/inspectionDetails/{id}")
    public String showVehicleInspectionDetails(@PathVariable int id, Model model){
        model.addAttribute("vehicleInspection", vehicleInspectionService.getById(id));
        model.addAttribute("vehicle", vehicleInspectionService.getById(id).getVehicle());
        return "inspectionDetails";
    }

    @GetMapping("/inspectionEdit/{id}")
    public String showUpdateVehicleInspectionForm(@PathVariable("id") int id, Model model){
        model.addAttribute("vehicleInspection", vehicleInspectionService.getById(id));
        model.addAttribute("vehicle", vehicleInspectionService.getById(id).getVehicle());
        return "editInspection";
    }

    @PostMapping("/updateVehicleInspection/{id}")
    public String updateInspection(@PathVariable("id") int id, @Valid VehicleInspection vehicleInspection,
                                   BindingResult result, Model model){
        if (result.hasErrors()) {
            vehicleInspection.setIdVehicleInspection(id);
            return "editInspection";
        }

        vehicleInspection.setIdVehicleInspection(id);
        vehicleInspectionService.save(vehicleInspection);

        model.addAttribute("vehicleInspection", vehicleInspectionService.getById(id));
        model.addAttribute("vehicle", vehicleInspectionService.getById(id).getVehicle());

        return "redirect:/inspectionDetails/{id}";
    }

    @GetMapping("/allVehicleInspections")
    public String showAllVehicleInspections(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        List <Vehicle> vehicles = vehicleService.findMyVehicles(user);
        model.addAttribute("inspections", vehicleInspectionService.findVehicleInspections(vehicles));
        return "allVehicleInspections";
    }

    @GetMapping("/addInspectionWithVehicleChoice")
    public String showCreateVehicleInspectionChoiceForm(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findUserByEmail(auth.getName());
        model.addAttribute("vehicleInspection", new VehicleInspection());
        model.addAttribute("vehicles", vehicleService.findMyVehicles(user));
        return "addInspectionWithVehicleChoice";
    }

    @PostMapping("/addInspectionWithVehicleChoice")
    public String createVehicleInspectionAfterVehicleChoice(@ModelAttribute VehicleInspection vehicleInspection){
        vehicleInspectionService.save(vehicleInspection);
        return "redirect:/allVehicleInspections";
    }

}
