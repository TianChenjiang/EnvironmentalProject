public interface RecordBlServiceImpl implements RecordBlService{
    private final RecordDataService recordDataService;
    @Autowired
    public
    void check(SpeciesCheckVo speciesCheckVo){

        PlantRecord plantRecord = recordDataService.getRecordById(speciesCheckVo.getId());
        PlantRecord.setState(RecordState.RECORDED);
        PlantRecord.setName(name);
        PlantRecord.setLng(lng);
        PlantRecord.setLat(lat);
        recordDataService.saveRecord(plantRecord);
    }
}