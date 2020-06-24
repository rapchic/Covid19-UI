
package com.analytics.UI;

import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

/**
 *
 * @author Mo
 */
@Route
@Theme(value = Lumo.class)
public class MainView extends SplitLayout{
    
    private SearchData1 searchData;
    
    private Country selectedCountry=null;
    private LocalDate SelectedFromDate=null;
    private LocalDate SelectedToDate=null;
    private Function selectedFunction;
    
    private SplitLayout firstHalf,secondHalf,lowTopThird,lowBotThird;
    private HorizontalLayout headTopLayout,headBotLayout;
    private HorizontalLayout tailTopLayout,tailBotLayout;
    private HorizontalLayout dataLayout,datasetLayout;
    
    private RadioButtonGroup<Function> radioGroup;
    ComboBox<Country> comboBox;
    DatePicker fromDate;
    DatePicker toDate;
    
    private TextArea textArea;
    private Grid<Country> grid;
    private Grid<Data> dgrid;
    
    public MainView(){
        
        searchData=new SearchData1();
        setOrientation(Orientation.VERTICAL);
        
        firstHalf=new SplitLayout();
        firstHalf.setOrientation(Orientation.VERTICAL);
        
        secondHalf=new SplitLayout();
        secondHalf.setOrientation(Orientation.VERTICAL);
        
        lowTopThird=new SplitLayout();
        lowTopThird.setOrientation(Orientation.VERTICAL);
        
        lowBotThird=new SplitLayout();
        lowBotThird.setOrientation(Orientation.VERTICAL);
        
        headTopLayout=new HorizontalLayout();
        headBotLayout=new HorizontalLayout();
        
        tailTopLayout=new HorizontalLayout();
        tailBotLayout=new HorizontalLayout();
        
        datasetLayout=new HorizontalLayout();
        dataLayout=new HorizontalLayout();
        
        headTopLayout.setWidth("80%");
        headTopLayout.getStyle().set("border", "5px solid #19bc9b");
  
        headBotLayout.setWidth("80%");
        headBotLayout.getStyle().set("border", "5px solid #19bc9b");
 
        tailTopLayout.setWidth("80%");
        tailTopLayout.getStyle().set("border", "5px solid #19bc9b");
        
        tailBotLayout.setWidth("80%");
        tailBotLayout.getStyle().set("border", "5px solid #19bc9b");
        
        datasetLayout.setWidth("80%");
        datasetLayout.setWidth("40%");
        datasetLayout.getStyle().set("border", "5px solid #19bc9b");
        
        dataLayout.setWidth("80%");
        dataLayout.setWidth("40%");
        dataLayout.getStyle().set("border", "5px solid #19bc9b");
        
        firstHalf.getStyle().set("padding", "1%");
        secondHalf.getStyle().set("padding", "0.5%");
        lowBotThird.getStyle().set("padding", "0.5%");
        lowTopThird.getStyle().set("padding", "0.5%");
        
        firstHalf.addToPrimary(headTopLayout);
        firstHalf.addToSecondary(headBotLayout);
        secondHalf.addToPrimary(tailTopLayout);
        secondHalf.addToSecondary(tailBotLayout);
        lowTopThird.addToPrimary(secondHalf);
        
        lowBotThird.addToPrimary(dataLayout);
        lowBotThird.addToSecondary(datasetLayout);
        
        lowTopThird.addToSecondary(lowBotThird);
        
        addToPrimary(firstHalf);
        addToSecondary(lowTopThird);
        
        init();
    }
    
    private void addHeadTop(){
        H2 header = new H2("Covid-19 Data Analytic Web Application");
        header.getElement().getStyle().set("text-align","center");
        header.getElement().getStyle().set("padding","5px");
        header.getElement().getStyle().set("color","purple");
        headTopLayout.addAndExpand(header);
        headTopLayout.getStyle().set("border", "5px solid #19bc9b");
    }
    
    private void addHeadBottom(){
        
        VerticalLayout leftLayout,middleLayout,rightLayout;
        HorizontalLayout topLayout,midLayout,botLayout;
      
        leftLayout=new VerticalLayout();
        middleLayout=new VerticalLayout();
        rightLayout=new VerticalLayout();
        
        topLayout=new HorizontalLayout();
        midLayout=new HorizontalLayout();
        botLayout=new HorizontalLayout();
        
        H3 header = new H3("List of Functions");
        header.getElement().getStyle().set("text-align","center");
        
        radioGroup = new RadioButtonGroup<>();
        List<Function> funcList = getFunctions();
        
        radioGroup.setRenderer(new TextRenderer<>(Function::getInstruction));
        radioGroup.setItems(funcList);
        radioGroup.setValue(funcList.get(0));
        selectedFunction=funcList.get(0);
        radioGroup.getElement().getStyle().set("align-items", "center");
        radioGroup.getElement().getStyle().set("justify-content", "center");
        
        radioGroup.addValueChangeListener(event -> {
            if (event.getValue() == null) {
                Notification.show("Please select a function");
                selectedFunction=null;
            } else {
                Notification.show("Selected:"+event.getValue().getId());
                selectedFunction=event.getValue();
                actionOnSelection(selectedFunction);
            }
        });
        
        Button button = new Button("Click To Load");
        button.getElement().getStyle().set("align-items", "center");
        button.getElement().getStyle().set("width", "150px");
        button.getElement().getStyle().set("height", "60px");
        button.getElement().getStyle().set("justify-content", "center");

        button.addClickListener(clickEvent -> {
                
            actionOnClick(selectedFunction);
        });
        
        //middleLayout.setSizeFull();
        leftLayout.setMargin(true);
        leftLayout.setSpacing(true);
        leftLayout.setPadding(true);
        leftLayout.setMinWidth("40%");
        leftLayout.addAndExpand(header,radioGroup);
        
        rightLayout.setMargin(true);
        rightLayout.setSpacing(true);
        rightLayout.setPadding(true);
        rightLayout.setMinWidth("30%");
        midLayout.add(button);
        midLayout.getElement().getStyle().set("align-items", "center");
        rightLayout.addAndExpand(new Div(),new Div(),new Div(),topLayout,midLayout,botLayout,new Div(),new Div());
        
        middleLayout.getStyle().set("align-items", "center");
        headBotLayout.addAndExpand(leftLayout,middleLayout,rightLayout);
        
    }
    
    private void addTailTop(){
        
        comboBox = new ComboBox<>();
        comboBox.setLabel("Select Country ");
        comboBox.getElement().getStyle().set("padding", "5px");
        comboBox.getElement().getStyle().set("max-width", "40%");
        Country[] CountriesList = searchData.getAllCountries();
        
        comboBox.setItemLabelGenerator(Country::getRealName);
        comboBox.setItems(CountriesList);
        tailTopLayout.getStyle().set("align-items", "center");
        tailTopLayout.addAndExpand(new Div(),comboBox,new Div());
        
        comboBox.addValueChangeListener(event -> 
                selectedCountry=(event.getValue())
        );
    }
    
    private void addTailBottom(){
        
        fromDate = new DatePicker();
        fromDate.setLabel("From:");
        LocalDate now = LocalDate.now();
        fromDate.setValue(now);
        fromDate.getElement().getStyle().set("padding", "5px");
        
        toDate = new DatePicker();
        toDate.setLabel("To:");
        LocalDate now1 = LocalDate.now();
        toDate.setValue(now1);
        toDate.getElement().getStyle().set("padding", "5px");
        
        tailBotLayout.addAndExpand(new Div(),fromDate,new Div(),toDate,new Div());
        
        fromDate.addValueChangeListener(event -> {
            if(isDateValid(event.getValue())){
                SelectedFromDate = (event.getValue());
                
            }else{
                SelectedFromDate = null;
                Notification.show("Date Not Valid");
            }
            
        });
        
        toDate.addValueChangeListener(event -> {
                if(isDateValid(event.getValue())){
                SelectedToDate = (event.getValue());
            }else{
                SelectedToDate = null;
                Notification.show("Date Not Valid");
            }
        
        });
        
    }
    
    private void addDatasetFrame(){

        grid = new Grid<>();
        grid.addColumn(Country::getRName).setHeader("Name").setWidth("2%");
        grid.addColumn(Country::getRegion).setHeader("Region").setWidth("2%");
        grid.addColumn(Country::getDataSet).setHeader("Data").setResizable(true);
        datasetLayout.addAndExpand(grid);
    }
    
    private void addDataFrame(){
        textArea = new TextArea("Raw Data:");
        textArea.getElement().getStyle().set("minHeight", "10%");
        //textArea.setPlaceholder("Write here ...");
        dataLayout.getElement().getStyle().set("minHeight", "20%");
        dataLayout.addAndExpand(textArea);
    }
    
    private void printDataInTA(Country[] countries){
        
        for(Country i:countries){
          
           textArea.setValue(textArea.getValue()+ "\n" + i.getName()+" "+i.getDataSet());
        }
    }
    
    public void init() {
        addHeadTop();
        addHeadBottom();
        addTailTop();
        addTailBottom();
        addDataFrame();
        //addDatasetFrame();
    }
    
    private List<Function> getFunctions(){
        List<Function> temp=new ArrayList<>();
        
        temp.add(new Function(1,"Display all countries","/get-all"));
        temp.add(new Function(2,"Display all data for a country","/get-all"));
        temp.add(new Function(3,"Display data for all countries on a specific date","/get-all"));
        temp.add(new Function(4,"Display data for a country on a specific date","/get-all"));
        temp.add(new Function(5,"Display data for a country in a period","/get-all"));
  
        return temp;
    }
    
    public LocalDate convertDate(LocalDate date){
        
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("M-d-yy");
        
        String temp = date.format(outputFormatter);
        return LocalDate.parse(temp, outputFormatter);
    }
    
    public boolean isDateValid(LocalDate date){
        return searchData.isDateValid(date);
    }
    
    public void actionOnSelection(Function function){
        
        switch(function.getId()){
            case 1: toDate.setEnabled(false);
                    fromDate.setEnabled(false);
                    comboBox.setEnabled(false);
                break;
            case 2: comboBox.setEnabled(true);
                    toDate.setEnabled(false);
                    fromDate.setEnabled(false);
                break;
            case 3: fromDate.setEnabled(true);
                    toDate.setEnabled(false);
                    comboBox.setEnabled(false);
                break;
            case 4:
                comboBox.setEnabled(true);
                fromDate.setEnabled(true);
                toDate.setEnabled(false);
                break;
            case 5:
                comboBox.setEnabled(true);
                fromDate.setEnabled(true);
                toDate.setEnabled(true);
                break;
            default:
                comboBox.setEnabled(true);
                fromDate.setEnabled(true);
                toDate.setEnabled(true);
                break;
        }
    }
    
    
    public void actionOnClick(Function function){
        
        switch(function.getId()){
            case 1:printDataInGrid(searchData.getAllCountries());
                break;
            case 2:if(selectedCountry!=null){printToGrid(selectedCountry);}else{ Notification.show("Select a Country");}
                break;
            case 3: if(SelectedFromDate!=null){printDataForAllOnDate(SelectedFromDate);}else{ Notification.show("Select a vaid date");}
                break;
            case 4: if(SelectedFromDate!=null){printDataForCountryonDate(selectedCountry,SelectedFromDate);}else{ Notification.show("Select a vaid date");}
                break;
            case 5: if(SelectedFromDate!=null && SelectedToDate!=null){printDataFromDateToDate(selectedCountry,SelectedFromDate,SelectedToDate);}else{ Notification.show("Select a vaid date");}
                break;
            default:printDataInTA(selectedCountry.getRealName()+" "+convertDate(SelectedFromDate)+" "+convertDate(SelectedToDate));
                break;
        }
    }
    
    //
    
    private void printDataForCountryonDate(Country country,LocalDate date){
        int retData=searchData.getCountryDataByDate(new Request(country.getName(),convertDate(date))).getStat();
        printNewDataInTA(String.valueOf(retData));
    }
    
    private void printDataInTA(String arg){
        
           textArea.setValue(textArea.getValue()+ "\n" + arg);
    }
    
    private void printNewDataInTA(String arg){
        
           textArea.setValue(arg);
        
    }
    
    private void printToGrid(Country country) {
        printDataToGrid(country);
        printCountryToGrid(country);
    }

    private void printDataForAllOnDate(LocalDate date){
        datasetLayout.removeAll();
        dgrid = new Grid<>();
        Data[] temp=searchData.getAllDataByDate(new Request(convertDate(date)));
        dgrid.addColumn(Data::getNameRef).setHeader("Country").setWidth("2%");
        dgrid.addColumn(Data::getRegion).setHeader("Region").setWidth("2%");
        dgrid.addColumn(Data::getStat).setHeader("Value").setWidth("2%");
        dgrid.setItems(temp);
        //dgrid.setMaxWidth("70%");
        datasetLayout.addAndExpand(dgrid);
    }
    
    private void printDataFromDateToDate(Country country, LocalDate from, LocalDate to){
        Data[] temp=searchData.getDataFromDateToDate(new Request(country.getName(),convertDate(from),convertDate(to)));
        datasetLayout.removeAll();
        dgrid = new Grid<>();
        
        dgrid.addColumn(Data::getDate).setHeader("Date").setWidth("2%");
        dgrid.addColumn(Data::getStat).setHeader("Value").setWidth("2%");
        dgrid.setItems(temp);
        dgrid.setMaxWidth("40%");
        datasetLayout.addAndExpand(dgrid);
    }
    
    private void printDataToGrid(Country country){
        
        datasetLayout.removeAll();
        dgrid = new Grid<>();
        Data[] temp=new Data[country.getDataSet().size()];
        temp=country.getDataSet().toArray(temp);
        
        dgrid.addColumn(Data::getDate).setHeader("Date").setWidth("2%");
        dgrid.addColumn(Data::getStat).setHeader("Value").setWidth("2%");
        dgrid.setItems(temp);
        dgrid.setMaxWidth("40%");
        datasetLayout.add(dgrid);
    }
    
    private void printCountryToGrid(Country country){
        
            grid = new Grid<>();
            grid.setItems(country);
            grid.addColumn(Country::getRName).setHeader("Name").setWidth("2%");
            grid.addColumn(Country::getRegion).setHeader("Region").setWidth("2%");
            grid.addColumn(Country::getLat).setHeader("Lat").setWidth("2%");
            grid.addColumn(Country::getLong).setHeader("Long").setWidth("2%");
            datasetLayout.addAndExpand(grid);
    }
    
    private void printDataInGrid(Country[] countries) {

        datasetLayout.removeAll();
        grid = new Grid<>();
        grid.setItems(countries);
        grid.addColumn(Country::getRName).setHeader("Name").setWidth("2%");
        grid.addColumn(Country::getRegion).setHeader("Region").setWidth("2%");
        grid.addColumn(Country::getLat).setHeader("Lat").setWidth("2%");
        grid.addColumn(Country::getLong).setHeader("Long").setWidth("2%");
        grid.addColumn(Country::getDataSet).setHeader("Data").setResizable(true);
        datasetLayout.addAndExpand(grid);
        
    }
    
    
}
