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
        if(pages.isEmpty()){
            return null;
        }
        return pages.get(pages.size() - 1);
    }

    public List<Object> getPage(int pageIndex) {
        if(pageIndex<0 || pageIndex >= getPageCount()){
            return new ArrayList<>();
        }
        return pages.get(pageIndex);
    }

    public int getPageCount() {
        return pages.size();
    }

    public int getRowsPerPage() {
        return rows;
    }
    public boolean removeRow(int pageIndex, int rowIndex){
        if(pageIndex>= 0&& pageIndex< getPageCount()){
            List<Object> page= pages.get(pageIndex);
            if(rowIndex>= 0 && rowIndex< page.size()){
                page.remove(rowIndex);
                return true;
            }
        }
        return false;
    }
    public void printAllData() {
        for (int i = 0; i < getPageCount(); i++) {
            System.out.println("Page " + (i + 1) + ":");
            for (Object row : getPage(i)) {
                System.out.println(row);
            }
        }
    }

}
