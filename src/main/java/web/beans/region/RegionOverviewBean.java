package web.beans.region;

import com.sun.org.apache.regexp.internal.RE;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import main.domain.Region;
import main.service.RegionService;
import web.core.helpers.ContextHelper;

/**
 * @author Sam
 */
@Named
@SessionScoped
public class RegionOverviewBean implements Serializable {

    @Inject
    private RegionService regionService;

    private Region region;
    private List<Region> regions;

    public void init() {
        if (!ContextHelper.isAjaxRequest()) {
            regions = regionService.getAll();
        }
    }

    public void save() {
        regionService.update(region);
        regions = regionService.getAll();
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
