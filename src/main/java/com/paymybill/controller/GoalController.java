package com.paymybill.controller;

import com.paymybill.controller.model.GenericResponse;
import com.paymybill.controller.model.GoalNoTargetDTO;
import com.paymybill.controller.model.GoalTargetDTO;
import com.paymybill.dao.model.Currency;
import com.paymybill.dao.model.Goal;
import com.paymybill.service.GoalService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Collection;
import java.util.UUID;

@Controller
@RequestMapping("/goal")
public class GoalController {

    private Logger logger = Logger.getLogger(this.getClass());

    private GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @InitBinder     /* Converts empty strings into null when a form is submitted */
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping("/create")
    public ModelAndView goalCreatePage(@RequestParam(required = false, name = "targetUuid") String targetUuid) {

        Collection<Currency> allCurrencies = goalService.getAllCurrencies();

        ModelAndView goalCreatePage = new ModelAndView("goal/create");

        // TODO think about UUID format check
        if (targetUuid != null) {
            goalCreatePage.addObject("goalTargetDTO", new GoalTargetDTO());
        } else {
            goalCreatePage.addObject("goalNoTargetDTO", new GoalNoTargetDTO());
        }
        goalCreatePage.addObject("allCurrencies", allCurrencies);
        return goalCreatePage;
    }

    @PostMapping("/create")
    public ResponseEntity createGoalNoTargetAction(@Valid @ModelAttribute("goalNoTargetDTO") GoalNoTargetDTO goalNoTargetDTO,
                                                    BindingResult result, Authentication authentication) {
        int errorCount = result.getErrorCount();
        if (errorCount != 0) {
            return ResponseEntity.ok(GenericResponse.createResponse(false, result.getAllErrors()));
        }

        Goal goal = goalService.registerNewGoal(goalNoTargetDTO);

        return ResponseEntity.ok(GenericResponse.createResponse(true, goal));
    }

    @PostMapping("/createWithTarget")
    public ResponseEntity createGoalTargetAction(@Valid @ModelAttribute("goalTargetDTO") GoalTargetDTO goalTargetDTO,
                                                BindingResult result, Authentication authentication) {
        int errorCount = result.getErrorCount();
        if (errorCount != 0) {
            return ResponseEntity.ok(GenericResponse.createResponse(false, result.getAllErrors()));
        }

        Goal goal = goalService.registerNewGoal(goalTargetDTO);

        return ResponseEntity.ok(GenericResponse.createResponse(true, goal));
    }

    @GetMapping("/view")
    public ModelAndView goalPage(@RequestParam(name = "uuid") String uuid) {
        UUID goalUuid;
        try {
            goalUuid = UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return new ModelAndView("redirect:/goal/view?error=wrong_id");
        }
        return new ModelAndView("index");
    }
}
