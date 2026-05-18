@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Setting screen layout
    setContentView(R.layout.admin_activity_add_employee);

    // =========================
    // UI INITIALIZATION
    // =========================
    // Finding all UI components from XML
    name = (EditText) findViewById(R.id.AddEmployeeName300);
    type = (Spinner) findViewById(R.id.DropDownMenu300);
    email = (EditText) findViewById(R.id.AddEmployeeEmail300);
    password = (EditText) findViewById(R.id.AddEmployeePassword300);
    specialty = (EditText) findViewById(R.id.AddSpeciality300);
    salary = (EditText) findViewById(R.id.AddEmployeeSalary300);
    special = (TextView) findViewById(R.id.specialityLabel300);
    addButton = (Button) findViewById(R.id.AddNewEmployeeButton300);

    // =========================
    // SPINNER / UI HANDLING
    // =========================
    // Changing fields according to selected employee type
    type.setOnItemSelectedListener(
        new AdapterView.OnItemSelectedListener() {

        public void onItemSelected(
            AdapterView<?> parent,
            View view,
            int position,
            long id) {

            String selectedItem =
                parent.getItemAtPosition(position)
                      .toString();

            // Chef and Head Chef need specialty
            if (selectedItem.equals("Chef")
                || selectedItem.equals("Head Chef")) {

                specialty.setEnabled(true);
                specialty.setInputType(
                    InputType.TYPE_CLASS_TEXT);

                specialty.setFocusable(true);
                specialty.setFocusableInTouchMode(true);

                special.setEnabled(true);

            } else {

                specialty.setEnabled(false);
                specialty.setInputType(
                    InputType.TYPE_NULL);

                specialty.setFocusable(false);

                special.setEnabled(false);
            }

            // Hall Manager and Head Chef need login
            if (selectedItem.equals("Hall Manager")
                || selectedItem.equals("Head Chef")) {

                email.setEnabled(true);
                email.setInputType(
                    InputType.TYPE_CLASS_TEXT);

                password.setEnabled(true);
                password.setInputType(
                    InputType.TYPE_CLASS_TEXT);

                check = true;

            } else {

                email.setEnabled(false);
                email.setInputType(
                    InputType.TYPE_NULL);

                password.setEnabled(false);
                password.setInputType(
                    InputType.TYPE_NULL);
            }
        }

        public void onNothingSelected(
            AdapterView<?> parent) {

        }
    });

    // =========================
    // BUTTON CLICK HANDLING
    // =========================
    addButton.setOnClickListener(
        new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            String specialtyText = "";
            boolean go = true;

            // =========================
            // VALIDATION LOGIC
            // =========================

            if (!type.getSelectedItem()
                     .toString()
                     .equals("Chef")) {

                specialtyText = "None";

            } else {

                specialtyText =
                    type.getSelectedItem()
                        .toString();
            }

            // Name validation
            if (name.getText()
                    .toString()
                    .length() <= 0) {

                name.setError(
                    "Name is Required");

                go = false;
            }

            // Email validation
            if (email.getText()
                     .toString()
                     .length() <= 0 &&

                (type.getSelectedItem()
                     .toString()
                     .equals("Head Chef")
                 ||

                 type.getSelectedItem()
                     .toString()
                     .equals("Hall Manager"))) {

                email.setError(
                    "Email Address is Required");

                go = false;
            }

            // Password validation
            if (password.getText()
                        .toString()
                        .length() <= 0 &&

                (type.getSelectedItem()
                     .toString()
                     .equals("Head Chef")
                 ||

                 type.getSelectedItem()
                     .toString()
                     .equals("Hall Manager"))) {

                password.setError(
                    "Password is Required");

                go = false;
            }

            // Salary validation
            if (salary.getText()
                      .toString()
                      .length() > 0 &&

                Integer.parseInt(
                    salary.getText()
                          .toString()) < 1) {

                salary.setError(
                    "Salary must be greater than 0");

                go = false;
            }

            // Specialty validation
            if (specialty.getText()
                         .toString()
                         .length() <= 0 &&

                type.getSelectedItem()
                    .toString()
                    .equals("Chef")) {

                specialty.setError(
                    "Specialty is Required");

                go = false;
            }

            // =========================
            // FIREBASE AUTHENTICATION
            // =========================
            if (go) {

                e = email.getText().toString();
                p = password.getText().toString();

                if (check) {

                    firebaseAuth
                    .createUserWithEmailAndPassword(e, p)

                    .addOnCompleteListener(
                        AddEmployeeActivity.this,

                        new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(
                            @NonNull
                            Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                Toast.makeText(
                                    AddEmployeeActivity.this,

                                    "User is Registered",

                                    Toast.LENGTH_SHORT)
                                    .show();

                            } else {

                                check1 = false;

                                Toast.makeText(
                                    AddEmployeeActivity.this,

                                    "Error "
                                    + task.getException()
                                          .toString(),

                                    Toast.LENGTH_SHORT)
                                    .show();
                            }
                        }
                    });
                }

                // =========================
                // FIREBASE DATABASE HANDLING
                // =========================
                if (check1) {

                    DatabaseReference mDatabase1 =
                        FirebaseDatabase
                        .getInstance()
                        .getReference();

                    // Getting Employee IDs
                    mDatabase1.child("Ids")
                    .addChildEventListener(
                        new ChildEventListener() {

                        @Override
                        public void onChildAdded(
                            DataSnapshot dataSnapshot,
                            String prevChildKey) {

                            if (dataSnapshot != null) {

                                eid.add(
                                    dataSnapshot
                                    .getValue(Long.class));
                            }
                        }

                        @Override
                        public void onChildChanged(
                            DataSnapshot dataSnapshot,
                            String s) {
                        }

                        @Override
                        public void onChildRemoved(
                            DataSnapshot dataSnapshot) {
                        }

                        @Override
                        public void onChildMoved(
                            DataSnapshot dataSnapshot,
                            String s) {
                        }

                        @Override
                        public void onCancelled(
                            DatabaseError databaseError) {
                        }
                    });

                    DatabaseReference mDatabase =
                        FirebaseDatabase
                        .getInstance()
                        .getReference();

                    // =========================
                    // EMPLOYEE CREATION
                    // =========================
                    mDatabase.child("Employee")
                    .addListenerForSingleValueEvent(
                        new ValueEventListener() {

                        public void onDataChange(
                            DataSnapshot dataSnapshot) {

                            DatabaseReference ref =
                                FirebaseDatabase
                                .getInstance()
                                .getReference();

                            if (E.size() == 0) {

                                ref.child("Ids")
                                   .child("Employeeid")
                                   .setValue(1);
                            }

                            Employee e1 =
                                new Employee(

                                String.valueOf(
                                    eid.get(0)),

                                name.getText()
                                    .toString(),

                                e,

                                p,

                                specialty.getText()
                                          .toString(),

                                salary.getText()
                                      .toString(),

                                type.getSelectedItem()
                                    .toString()
                            );

                            // =========================
                            // DATABASE SAVE
                            // =========================
                            ref.child("Employee")
                               .child(
                                   String.valueOf(
                                       eid.get(0)))
                               .setValue(e1);

                            ref.child("Ids")
                               .child("Employeeid")
                               .setValue(
                                   eid.get(0) + 1);

                            Toast.makeText(
                                AddEmployeeActivity.this,

                                "Employee Added Successfully",

                                Toast.LENGTH_SHORT)
                                .show();

                            eid.clear();

                            // =========================
                            // NAVIGATION
                            // =========================
                            Intent i = new Intent(
                                AddEmployeeActivity.this,

                                AdminPanelActivity.class);

                            startActivity(i);
                        }

                        @Override
                        public void onCancelled(
                            @NonNull
                            DatabaseError databaseError) {

                        }
                    });

                    // =========================
                    // FETCHING EMPLOYEE DATA
                    // =========================
                    mDatabase.child("Employee")
                    .addChildEventListener(
                        new ChildEventListener() {

                        public void onChildAdded(
                            DataSnapshot dataSnapshot,
                            String previousKey) {

                            Employee item =
                                dataSnapshot.getValue(
                                    Employee.class);

                            E.add(item);
                        }

                        public void onChildChanged(
                            DataSnapshot dataSnapshot,
                            String s) {
                        }

                        public void onChildRemoved(
                            DataSnapshot dataSnapshot) {
                        }

                        public void onChildMoved(
                            DataSnapshot dataSnapshot,
                            String s) {
                        }

                        @Override
                        public void onCancelled(
                            @NonNull
                            DatabaseError databaseError) {

                        }
                    });
                }
            }
        }
    });
}
//Employee Creation Logic
if(selectedItem.equals("Delivery Boy")){
    // Enable fields
}