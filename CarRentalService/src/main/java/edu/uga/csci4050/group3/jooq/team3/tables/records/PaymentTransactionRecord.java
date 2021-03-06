/**
 * This class is generated by jOOQ
 */
package edu.uga.csci4050.group3.jooq.team3.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(value    = { "http://www.jooq.org", "3.2.0" },
                            comments = "This class is generated by jOOQ")
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class PaymentTransactionRecord extends org.jooq.impl.UpdatableRecordImpl<edu.uga.csci4050.group3.jooq.team3.tables.records.PaymentTransactionRecord> implements org.jooq.Record9<java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> {

	private static final long serialVersionUID = -401676541;

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.id</code>. 
	 */
	public void setId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.id</code>. 
	 */
	public java.lang.Integer getId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.uid</code>. 
	 */
	public void setUid(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.uid</code>. 
	 */
	public java.lang.String getUid() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.date</code>. 
	 */
	public void setDate(java.lang.Integer value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.date</code>. 
	 */
	public java.lang.Integer getDate() {
		return (java.lang.Integer) getValue(2);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.method</code>. 
	 */
	public void setMethod(java.lang.String value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.method</code>. 
	 */
	public java.lang.String getMethod() {
		return (java.lang.String) getValue(3);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.description</code>. 
	 */
	public void setDescription(java.lang.String value) {
		setValue(4, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.description</code>. 
	 */
	public java.lang.String getDescription() {
		return (java.lang.String) getValue(4);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.user</code>. 
	 */
	public void setUser(java.lang.String value) {
		setValue(5, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.user</code>. 
	 */
	public java.lang.String getUser() {
		return (java.lang.String) getValue(5);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.reason</code>. 
	 */
	public void setReason(java.lang.String value) {
		setValue(6, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.reason</code>. 
	 */
	public java.lang.String getReason() {
		return (java.lang.String) getValue(6);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.comments</code>. 
	 */
	public void setComments(java.lang.String value) {
		setValue(7, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.comments</code>. 
	 */
	public java.lang.String getComments() {
		return (java.lang.String) getValue(7);
	}

	/**
	 * Setter for <code>team3.PAYMENT_TRANSACTION.status</code>. 
	 */
	public void setStatus(java.lang.String value) {
		setValue(8, value);
	}

	/**
	 * Getter for <code>team3.PAYMENT_TRANSACTION.status</code>. 
	 */
	public java.lang.String getStatus() {
		return (java.lang.String) getValue(8);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record9 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> fieldsRow() {
		return (org.jooq.Row9) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row9<java.lang.Integer, java.lang.String, java.lang.Integer, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String> valuesRow() {
		return (org.jooq.Row9) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.UID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field3() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.DATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field4() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.METHOD;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field5() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.DESCRIPTION;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field6() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.USER;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field7() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.REASON;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field8() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.COMMENTS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field9() {
		return edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION.STATUS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getUid();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value3() {
		return getDate();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value4() {
		return getMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value5() {
		return getDescription();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value6() {
		return getUser();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value7() {
		return getReason();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value8() {
		return getComments();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value9() {
		return getStatus();
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached PaymentTransactionRecord
	 */
	public PaymentTransactionRecord() {
		super(edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION);
	}

	/**
	 * Create a detached, initialised PaymentTransactionRecord
	 */
	public PaymentTransactionRecord(java.lang.Integer id, java.lang.String uid, java.lang.Integer date, java.lang.String method, java.lang.String description, java.lang.String user, java.lang.String reason, java.lang.String comments, java.lang.String status) {
		super(edu.uga.csci4050.group3.jooq.team3.tables.PaymentTransaction.PAYMENT_TRANSACTION);

		setValue(0, id);
		setValue(1, uid);
		setValue(2, date);
		setValue(3, method);
		setValue(4, description);
		setValue(5, user);
		setValue(6, reason);
		setValue(7, comments);
		setValue(8, status);
	}
}
