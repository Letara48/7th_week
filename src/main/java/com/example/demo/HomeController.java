package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
public class HomeController {
    @Autowired
    Accountrepository accountrepository;

    @Autowired
    Personrepository personrepository;

    @Autowired
    Balancerepository balancerepository;

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("amounts", accountrepository.findAll());
        model.addAttribute("persons", personrepository.findAll());
        return "index";
    }
    @GetMapping("/addPerson")
    public String RegistrationForm(Model model){
        model.addAttribute("person", new Person());
        return "registform";

    }
    @PostMapping("/processPerson")
    public String processpersonForm(@Valid @ModelAttribute("person")  Person person,
                                    BindingResult result, Model model){
        model.addAttribute("person", person);
        if (result.hasErrors()){
            return "registform";
        }
        personrepository.save(person);
        model.addAttribute("message", "Account Created");
        return "redirect:/registlist";
    }
//    @RequestMapping("/detail/{id}")
//    public String showperson(@PathVariable("id") long id, Model model)
//    {
//        model.addAttribute("person", personrepository.findById(id).get());
//        return "showregist";
//    }
    @RequestMapping("/update/{id}")
    public String updatePerson(@PathVariable("id") long id,
                               Model model){
        model.addAttribute("person", personrepository.findById(id).get());
        model.addAttribute("accounts", accountrepository.findAll());
        return "registform";
    }
    @RequestMapping("/delete/{id}")
    public String delPerson(@PathVariable("id") long id) {
        personrepository.deleteById(id);
        return "redirect:/loginform";
    }
    @RequestMapping("/loginform")
    public String loginPerson(Model model){
    model.addAttribute("account", new Account());
    model.addAttribute("persons", personrepository.findAll());
    return "loginform";
    }
    @PostMapping("/processLogin")
    public String processloginForm(@Valid Account account,
                                    BindingResult result){
        if (result.hasErrors()){
            return "loginform";
        }
        accountrepository.save(account);
        return "redirect:/account";
    }
    @RequestMapping("/account")
    public String accountform(Model model, @PathVariable(name="name") String name){
        model.addAttribute("persons", personrepository.findByname("name"));
        return "accountlist";

    }
    @PostMapping("/processwithdrawal")
    public String withdrawform(Model model, @Valid Balance balance,
    BindingResult result){
        if (result.hasErrors()){
            return "withdrawalform";
        }
        balancerepository.save(balance);
        return "redirect:/accountlist";
    }
    @PostMapping("/processdeposit")
    public String depositform(Model model, @Valid Balance balance,
                               BindingResult result){
        if (result.hasErrors()){
            return "depositform";
        }
        balancerepository.save(balance);
        return "redirect:/accountlist";
    }

}



