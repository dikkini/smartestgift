package com.smartestgift.controller;

import com.google.gson.Gson;
import com.smartestgift.controller.model.Response;
import com.smartestgift.controller.model.GiftPage;
import com.smartestgift.dao.model.Gift;
import com.smartestgift.dao.model.GiftCategory;
import com.smartestgift.dao.model.GiftShop;
import com.smartestgift.dao.model.SmartUserGift;
import com.smartestgift.exception.EmailBusyException;
import com.smartestgift.exception.UserHasGiftException;
import com.smartestgift.exception.UsernameBusyException;
import com.smartestgift.exception.WrongGiftDateException;
import com.smartestgift.service.GiftService;
import com.smartestgift.service.SmartUserService;
import com.smartestgift.utils.ApplicationConstants;
import com.smartestgift.utils.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static com.smartestgift.utils.ResponseMessages.*;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

/**
 * Created by dikkini on 14.02.14.
 * Email: dikkini@gmail.com
 */
@Controller
@RequestMapping("/gifts")
public class GiftController {

    @Autowired
    private GiftService giftService;

    @Autowired
    private SmartUserService smartUserService;

    @Autowired
    private Gson gson;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView giftCategories() {
        List<GiftCategory> allGiftCategories = giftService.findAllGiftCategories();
        ModelAndView mav = new ModelAndView("gifts/gifts");
        mav.addObject("allGiftCategories", allGiftCategories);
        return mav;
    }

    @RequestMapping(value = "/changePage", headers="Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody GiftPage changePage(@RequestParam(required = true, value = "countAll") Long countAll,
                                           @RequestParam(required = true, value = "pageNum") int pageNum,
                                           @RequestParam(required = true, value = "pageSize") int pageSize,
                                           @RequestParam(required = true, value = "categoryCode") String categoryCode) {
        return giftService.getPageOfGifts(countAll, pageNum, pageSize, categoryCode);
    }


    @RequestMapping(value = "/my", method = RequestMethod.GET)
    public ModelAndView myGifts(Authentication authentication) {
        Set<SmartUserGift> gifts = smartUserService.findUserByUsername(authentication.getName()).getSmartUserGifts();
        return new ModelAndView("gifts/my", "gifts", gifts);
    }

    @RequestMapping(value = "/{giftUuid}", method = RequestMethod.GET)
    public ModelAndView giftPage(@PathVariable String giftUuid) {
        // TODO check what to do with giftcategorycode
        Gift gift = giftService.findGiftByUuid(giftUuid);
        List<GiftShop> giftShops = giftService.findGiftShops(giftUuid);
        return new ModelAndView("gifts/gift").addObject("gift", gift).addObject("giftShops", giftShops);
    }

    // TODO add event to news feed
    // TODO sending email to a friends of users if option checked true to send
    // TODO check valid end date of gift collaboration
    @RequestMapping(value = "/wantGift", headers="Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody Response wantGift(Authentication authentication,
                                               @RequestParam(required = true, value = "giftShopUuid") String giftShopUuid,
                                               @RequestParam(required = true, value = "endDate") String endDateStr) {
        // TODO проверить uuidы или проверить проверку на правильность uuidов
/*        if (!isUUID(giftUuid) || !isUUID(shopUuid)) {
            result.setSuccess(false);
            result.addError(ResponseMessages.INTERNAL_ERROR);
            return result;
        }*/

        // TODO при локализации севрсиа, добавить в форматирование даты текущую локаль языка
        Date endDate;
        try {
            endDate = new SimpleDateFormat(ApplicationConstants.INPUT_DATE_FORMAT_PATTERN).parse(endDateStr);
        } catch (ParseException e) {
            throw new WrongGiftDateException("Wrong date of gift", BAD_REQUEST.value());
        }

        if (!giftService.hasSmartUserGiftShop(smartUserService.findUserByUsername(authentication.getName()), giftShopUuid)) {
            giftService.addGiftShopToUserWishes(smartUserService.findUserByUsername(authentication.getName()), giftShopUuid, endDate);
        } else {
            throw new UserHasGiftException("User has this gift", BAD_REQUEST.value());
        }

        return Response.createResponse(OK);
    }

    @RequestMapping(value = "/unWantGift", method = RequestMethod.POST)
    public @ResponseBody
    Response unWantGift(Authentication authentication,
                                                 @RequestParam(required = true, value = "giftshopuuid")
                                                 String giftShopUuid) {
        // TODO проверить uuidы или проверить проверку на правильность uuidов
//        if (!isUUID(giftShopUuid)) {
//            result.setSuccess(false);
//            result.addError(ResponseMessages.INTERNAL_ERROR);
//            return result;
//        }
        if (giftService.hasSmartUserGiftShop(smartUserService.findUserByUsername(authentication.getName()), giftShopUuid)) {
            giftService.deleteGiftFromUser(smartUserService.findUserByUsername(authentication.getName()), giftShopUuid);
        } else {
            throw new UserHasGiftException("User has not this gift", BAD_REQUEST.value());
        }

        return Response.createResponse(OK);
    }

    @RequestMapping(value = "/randomGift", method = RequestMethod.POST)
    public String getRandomGift() {
        // TODO do :-)
        return "redirect:/gifts/235334";
    }

    @RequestMapping(value = "/getFindGiftPage", headers="Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody GiftPage getFindGiftPage(Authentication authentication,
                                                @RequestParam(required = true, value = "countAll") Long countAll,
                                                @RequestParam(required = true, value = "searchString") String searchString,
                                                @RequestParam(required = true, value = "pageNum") int pageNum,
                                                @RequestParam(required = true, value = "pageSize") int pageSize) {
        // TODO security checks
        return giftService.getPageOfGiftsBySearchString(countAll, pageNum, pageSize,
                searchString);
    }

    @RequestMapping(value = "/findGiftShops", headers="Accept=application/json", method = RequestMethod.POST)
    public @ResponseBody List<GiftShop> findGiftShops(Authentication authentication,
                                             @RequestParam(required = true, value = "giftUuid") String giftUuid) {
        // TODO security checks
        return giftService.findGiftShops(giftUuid);
    }

    @ExceptionHandler(value=UserHasGiftException.class )
    public @ResponseBody ResponseEntity handleUserHasGiftException(HttpServletResponse response) {
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value=WrongGiftDateException.class)
    public @ResponseBody ResponseEntity handleWrongGiftDateException(HttpServletResponse response) {
        return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
    }
}
