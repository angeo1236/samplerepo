public class StoreListAdapter extends ArrayAdapter {
    int posnew=0;
    String nameis="";
    RoomDatacalling roomDatacalling =new RoomDatacalling();
    ArrayList<shelf_product_savemodel> productnameandbarcode=new ArrayList<>();
    ArrayList<shelf_product_savemodel>senditems;

    private List<shelf_product_savemodel> dataList;
    private Context mContext;
    private int itemLayout;
    clickinterface click;



    private StoreListAdapter.ListFilter listFilter = new StoreListAdapter.ListFilter();

    public StoreListAdapter(Context context, int resource, List<shelf_product_savemodel> storeDataLst,clickinterface clicktik) {
        super(context,resource,storeDataLst);

        this. dataList = storeDataLst;
       this. mContext = context;
      this.  itemLayout = resource;
      this.  click=clicktik;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public shelf_product_savemodel getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.row, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.lbl_name);
        strName.setText(dataList.get(position).getProductname()+"-"+dataList.get(position).getBarcode());
        posnew=position;
        nameis=    getItem(position).getProductname();
        strName.setTag(position);
        strName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int positionnew = (Integer) view.getTag();
                senditems= new ArrayList<>();
                senditems.add(dataList.get(positionnew));
                click.arraynew(senditems);
            }
        });




        return view;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return listFilter;
    }

    public class ListFilter extends Filter {
        private Object lock = new Object();

        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();

            if (prefix == null || prefix.length() == 0) {
                synchronized (lock) {
                    results.values = new ArrayList<String>();
                    results.count = 0;
                }
            } else {
                final String searchStrLowerCase = prefix.toString().toLowerCase();

                //Call to database to get matching records using orm

productnameandbarcode.clear();
                roomDatacalling.getproductname(searchStrLowerCase, 1, productnameandbarcode);



                results.values = productnameandbarcode;
                results.count = productnameandbarcode.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results.values != null) {
dataList.clear();
                dataList = (ArrayList<shelf_product_savemodel>)results.values;
                Log.i("sanutest","sanutest"+dataList.size());
            } else {
                dataList = null;
            }
            if (dataList.size()> 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }

    }
}