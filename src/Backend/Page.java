package Backend;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private List<List<Object>> pages;
    private int rows;

    public Page(int rows){
        this.pages= new ArrayList<>();
        this.rows=rows;
    }

    public void addRow(List<Object> data){
        if(pages.isEmpty()|| getLastPage().size()==rows){
            pages.add(new ArrayList<>());
        }
        getLastPage().add(data);
    }

    private List<Object> getLastPage() {
        return pages.get(pages.size() - 1);
    }

    public List<Object> getPage(int pageIndex) {
        return pages.get(pageIndex);
    }

    public int getPageCount() {
        return pages.size();
    }

    public int getRowsPerPage() {
        return rows;
    }

}
