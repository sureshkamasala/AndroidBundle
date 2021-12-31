AndroidBundle is an open java framework for Android to store and retreive values during screen transition(One Activity to Another Activity)

Sample Code:

Set Value to ABundle:
        ArrayList<String> list = .... 
        Intent intent = new Intent(context, class);
        intent.putExtra(ABundle.KEY, ABundle.getInstance().set(list));
  
Get Values from the ABundle
        Bundle bundle = getIntent().getExtras();
        ArrayList list = ABundle.getInstance().get(bundle.getString(ABundle.KEY), ArrayList.class);
