package com.homeoffice.technicaltask.stepdefinitions;

import com.homeoffice.technicaltask.pages.AboutYourCarPage;
import com.homeoffice.technicaltask.pages.BasePage;
import com.homeoffice.technicaltask.pages.ValueMyCarPage;
import com.homeoffice.technicaltask.utils.FileReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;

@Slf4j
public class CarValuationSteps {

    @Autowired
    private BasePage basePage;

    private List<String> results = new LinkedList<>();

    @Given("the customer is on cazoo's value my car page")
    public void the_customer_enters_their_car_registration_number() {
        basePage.navigateToCazoo();
        if (ValueMyCarPage.ACCEPT_ALL.isDisplayed()) {
            ValueMyCarPage.ACCEPT_ALL.click();
        }
    }

    @When("the customer performs quick valuation")
    public void their_perform_quick_valuation() {
        List<String> regNumbers = FileReader.readInputFiles();
        List<String> outputLines = FileReader.readOutputFiles();

        regNumbers.forEach(expectedRegNumber -> {
            ValueMyCarPage.ENTER_REG.setText(expectedRegNumber);
            ValueMyCarPage.START_VALUATION.click();

            if (AboutYourCarPage.REGISTRATION_NUMBER.isDisplayedAfterWait()) {
                String actualRegNumber = AboutYourCarPage.REGISTRATION_NUMBER.getText().replace("Reg: ", "");
                String carSummary = AboutYourCarPage.CAR_SUMMARY.getText().replace("Make/model: ", "");

                Optional<String> matchedLine = outputLines.stream()
                        .map(line -> line.replaceAll(",", " "))
                        .filter(line -> line.contains(expectedRegNumber))
                        .findFirst();

                matchedLine.ifPresent(line -> {
                    assertThat(line, containsString(carSummary));
                    assertThat(line, containsString(actualRegNumber));
                    results.add("The car details matches...reg number is: " + actualRegNumber + " and summary is: " + carSummary);
                });
            } else {
                results.add("The car registration number " + expectedRegNumber + " doesn't match :(");
            }
            basePage.navigateToCazoo();
        });
    }

    @Then("the car registration match results are displayed")
    public void the_car_registration_details_are_displayed() {
        results.forEach(log::info);
    }
}
