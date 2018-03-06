package com.example.firstvaadin;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.ui.dd.HorizontalDropLocation;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8909458605636406742L;
	private CustomerService service= new CustomerService().getInstance();
	private Grid grid = new Grid();
	private TextField filterText = new TextField();
	private Button clearFilterTxtBtn =new Button(FontAwesome.TIMES);
	private CustomerFormController form = new CustomerFormController(this);
	
    @Override
    protected void init(VaadinRequest vaadinRequest) {
        final VerticalLayout layout= new VerticalLayout() ;
        
        final TextField name = new TextField();
        name.setCaption("Type your name here:");


		// filter stuff
		filterText.setInputPrompt("filter by name...");
		filterText.addTextChangeListener(e -> {
			 grid.setContainerDataSource(new BeanItemContainer<>(Customer.class,
			            service.findAll(e.getText())));
		});

		// grid stuff
		grid.setColumns("firstName", "lastName", "email");
		updateList();
		
		//clearFilterButton stuff
		clearFilterTxtBtn.setDescription("Clear the current filter");
		clearFilterTxtBtn.addClickListener(e->{
			filterText.clear();
			updateList();
		});
		
		//filtering layout
		CssLayout filtering = new CssLayout();
		filtering.addComponents(filterText,clearFilterTxtBtn);
		filtering.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		
		// add to layout, and layout config
		HorizontalLayout main = new HorizontalLayout(grid, form);
		main.setMargin(true);
		main.setSpacing(true);
		main.setSizeFull();
		grid.setSizeFull();
		layout.addComponents(filtering, main);

		layout.setMargin(true);
        setContent(layout);
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
    
    public void updateList() {
    	List<Customer> customers = service.findAll(filterText.getValue());
        grid.setContainerDataSource(new BeanItemContainer<>(Customer.class, customers));
    }
    
    
}
