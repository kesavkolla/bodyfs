package com.bodyfs.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.compass.core.CompassDetachedHits;
import org.compass.core.CompassSearchSession;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.api.Button;
import org.zkoss.zul.api.Checkbox;
import org.zkoss.zul.api.Textbox;

import com.bodyfs.PMF;
import com.bodyfs.dao.IPageDAO;
import com.bodyfs.model.GeneralInfo;
import com.bodyfs.model.Page;
import com.bodyfs.model.Person;
import com.bodyfs.model.PersonType;
import com.bodyfs.ui.util.CustSearchOptions;

public class CustomerSearchComposer extends GenericForwardComposer {

	private static final long serialVersionUID = -7039984932259770671L;

	private static Log LOGGER = LogFactory.getLog(MainWindowComposer.class);

	// private IPersonDAO personDAO = (IPersonDAO)
	// SpringUtil.getBean("personDAO");

	AnnotateDataBinder binder;

	Checkbox typePre;
	Checkbox typePost;
	Checkbox typeCurrent;

	Textbox smrtTextbox;
	Button smrtSrch;

	Button refineSearch;
	Button newSearch;

	Listbox persons;

	CustSearchOptions options = new CustSearchOptions();

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId() + "Ctrl", this, true);
		binder = new AnnotateDataBinder(comp);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Collection<Person> resultSet = new ArrayList<Person>();
		try {
			Query query = pm.newQuery(Person.class);
			resultSet = (Collection<Person>) query.execute();
			resultSet = pm.detachCopyAll(resultSet);
			// persons.setModel(new ListModelList(resultSet));
			this.page.setAttribute("resultSet_", resultSet);
		} finally {
			pm.close();
		}
		binder.loadAll();
	}

	public void onOK$smrtTextbox(Event evt) throws Exception {
		searchSmart();
	}

	public void searchSmart() {
		if (smrtTextbox.getValue() == null || smrtTextbox.getValue().trim().equals("")) {
			return;
		}

		CompassSearchSession compassSession = PMF.getCompass().openSearchSession();
		// PersistenceManager pm = PMF.get().getPersistenceManager();
		smrtTextbox.getValue();
		CompassDetachedHits hits = compassSession.find(smrtTextbox.getValue()).detach();
		// hits = hits.detach();
		compassSession.close();
		final LinkedList<Person> resultSet = new LinkedList<Person>();
		for (int i = 0; i < hits.length(); i++) {
			if (hits.data(i) instanceof Person) {
				resultSet.add((Person) hits.data(i));
			}
		}
		persons.setModel(new ListModelList(resultSet));
	}

	@SuppressWarnings("unchecked")
	public List getResults() {
		return new ArrayList();
	}

	// public void

	public void getSearchQueryString(CustSearchOptions searchOptions) {

		StringBuffer qry = new StringBuffer("SELECT  FROM " + Person.class.getName() + " p,"
				+ GeneralInfo.class.getName() + " ginfo");
		qry.append(" WHERE p.id = ginfo.personId");
		if (searchOptions.getFirstName() != null && searchOptions.getFirstName().length() > 0) {
			qry.append(" AND p.firstName LIKE '").append(searchOptions.getFirstName()).append("%'");
		}
		if (searchOptions.getLastName() != null && searchOptions.getLastName().length() > 0) {
			qry.append(" AND p.lastName LIKE '").append(searchOptions.getLastName()).append("%'");
		}
		if (searchOptions.getEmail() != null && searchOptions.getEmail().length() > 0) {
			qry.append(" AND p.email LIKE '").append(searchOptions.getEmail()).append("%'");
		}
		if (searchOptions.getZip() != null && searchOptions.getZip().length() > 0) {
			qry.append(" AND ginfo.zipcode LIKE '%").append(searchOptions.getZip()).append("%'");
		}
		if (typePre.isChecked() || typeCurrent.isChecked() || typePost.isChecked()) {
			qry.append(" AND (");
			if (typePre.isChecked())
				qry.append(" personType = '" + PersonType.PRE_USER + "' OR");
			if (typeCurrent.isChecked())
				qry.append(" personType = '" + PersonType.USER + "' OR");
			if (typePost.isChecked())
				qry.append(" personType = '" + PersonType.POST_USER + "' OR");
			qry.setLength(qry.length() - 2);
			qry.append(" ) ");
		}

	}

	public void onClick$smrtSrch() throws Exception {
		searchSmart();
	}

	public void onPersonClick(final ForwardEvent event) {
		final IPageDAO pageDAO = (IPageDAO) SpringUtil.getBean("pageDAO");
		final Listcell cell = (Listcell) event.getOrigin().getTarget();
		LOGGER.debug("Navigating to patient id: " + cell.getValue());
		final Page page = pageDAO.getById("patientview");
		execution.sendRedirect(page.getPath() + "?id=" + cell.getValue());
	}
}
