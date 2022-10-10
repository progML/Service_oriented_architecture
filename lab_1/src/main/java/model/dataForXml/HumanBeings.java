package model.dataForXml;

import model.HumanBeing;
import org.simpleframework.xml.ElementList;
import java.util.List;

public class HumanBeings {
    @ElementList
    private List<HumanBeing> humanBeings = null;

    public List<HumanBeing> getHumanBeings() {
        return humanBeings;
    }

    public void setHumanBeings(List<HumanBeing> humanBeings) {
        this.humanBeings = humanBeings;
    }
}

