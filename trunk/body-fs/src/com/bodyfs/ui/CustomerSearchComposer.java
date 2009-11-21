package com.bodyfs.ui;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.compass.core.CompassHits;
import org.compass.core.CompassSearchSession;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.api.Button;
import org.zkoss.zul.api.Textbox;

import com.bodyfs.PMF;
import com.bodyfs.model.Person;

public class CustomerSearchComposer extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7039984932259770671L;

	private static Log LOGGER = LogFactory.getLog(MainWindowComposer.class);

	AnnotateDataBinder binder;

	Textbox smrtTextbox;

	Button smrtSearchbtn;

	Listbox persons;

	SearchOptions person = new SearchOptions();

	public Collection<Person> resultSet = new ArrayList<Person>();

	public Collection<Person> getResultSet() {
		return resultSet;
	}

	public void setResultSet(Collection<Person> resultSet) {
		this.resultSet = resultSet;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void doAfterCompose(final Component comp) throws Exception {
		super.doAfterCompose(comp);
		comp.setAttribute(comp.getId() + "Ctrl", this, true);
		binder = new AnnotateDataBinder(comp);
		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			Query query = pm.newQuery(Person.class);
			resultSet = (Collection<Person>) query.execute();
			resultSet = pm.detachCopyAll(resultSet);
			// extendedSearch.setVisible(false);
		} finally {
			pm.close();
		}
		binder.loadAll();
	}

	public void searchSmart() {
		CompassSearchSession compassSession = PMF.getCompass().openSearchSession();
		// PersistenceManager pm = PMF.get().getPersistenceManager();
		smrtTextbox.getValue();
		CompassHits hits = compassSession.find(smrtTextbox.getValue());
		setResultSet(null);
		for (int i = 0; i < hits.length(); i++) {
			resultSet.add((Person) hits.data(i));
		}
		// resultSet = pm.detachCopyAll(resultSet);
		LOGGER.error("Amit- " + resultSet.size());
		persons.setModel(new ListModelList(resultSet));
		binder.loadComponent(persons);

	}

	// public void

	public void extendedSearch(SearchOptions searchOptions) {

		StringBuffer qry = new StringBuffer("SELECT USER_DETAIL.* FROM USER_DETAIL, USER_ROLE");
		qry.append(" WHERE USER_DETAIL.ROLE_ID = USER_ROLE.ROLE_ID");

		if (searchOptions.getFirstName() != null && searchOptions.getFirstName().length() > 0) {
			qry.append(" AND FIRST_NAME LIKE '").append(searchOptions.getFirstName()).append("%'");
		}
		if (searchOptions.getLastName() != null && searchOptions.getLastName().length() > 0) {
			qry.append(" AND LAST_NAME LIKE '").append(searchOptions.getLastName()).append("%'");
		}
		if (searchOptions.getEmail() != null && searchOptions.getEmail().length() > 0) {
			qry.append(" AND EMAIL_ADDRESS LIKE '").append(searchOptions.getEmail()).append("%'");
		}
		if (searchOptions.getZip() != null && searchOptions.getZip().length() > 0) {
			qry.append(" AND COURSES LIKE '%").append(searchOptions.getZip()).append("%'");
		}
		if (searchOptions.getCustomerType() != null) {
			qry.append(" AND DATE_OF_JOINING >=? ");
		}
		if (searchOptions.getTrmtDateFrom() != null && searchOptions.getTrmtDateTo() != null) {
			qry.append(" AND DATE_OF_START >=? ");
		}

	}

	public void onClick$smrtSearchbtn() throws Exception {
		// searchSmart();

	}

	public void onOK$smrtTextbox(Event evt) throws Exception {
		searchSmart();
	}

	public class SearchOptions implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 7148090100102182159L;
		String firstName;
		String lastName;
		String customerType;
		String zip;

		String email;

		String ageGroup;

		Date trmtDateFrom;
		Date trmtDateTo;

		/**
		 * @return the firstName
		 */
		public String getFirstName() {
			return firstName;
		}

		/**
		 * @param firstName
		 *            the firstName to set
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		/**
		 * @return the lastName
		 */
		public String getLastName() {
			return lastName;
		}

		/**
		 * @param lastName
		 *            the lastName to set
		 */
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		/**
		 * @return the customerType
		 */
		public String getCustomerType() {
			return customerType;
		}

		/**
		 * @param customerType
		 *            the customerType to set
		 */
		public void setCustomerType(String customerType) {
			this.customerType = customerType;
		}

		/**
		 * @return the zip
		 */
		public String getZip() {
			return zip;
		}

		/**
		 * @param zip
		 *            the zip to set
		 */
		public void setZip(String zip) {
			this.zip = zip;
		}

		/**
		 * @return the email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * @param email
		 *            the email to set
		 */
		public void setEmail(String email) {
			this.email = email;
		}

		/**
		 * @return the ageGroup
		 */
		public String getAgeGroup() {
			return ageGroup;
		}

		/**
		 * @param ageGroup
		 *            the ageGroup to set
		 */
		public void setAgeGroup(String ageGroup) {
			this.ageGroup = ageGroup;
		}

		/**
		 * @return the trmtDateFrom
		 */
		public Date getTrmtDateFrom() {
			return trmtDateFrom;
		}

		/**
		 * @param trmtDateFrom
		 *            the trmtDateFrom to set
		 */
		public void setTrmtDateFrom(Date trmtDateFrom) {
			this.trmtDateFrom = trmtDateFrom;
		}

		/**
		 * @return the trmtDateTo
		 */
		public Date getTrmtDateTo() {
			return trmtDateTo;
		}

		/**
		 * @param trmtDateTo
		 *            the trmtDateTo to set
		 */
		public void setTrmtDateTo(Date trmtDateTo) {
			this.trmtDateTo = trmtDateTo;
		}

	}

}
