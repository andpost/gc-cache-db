export interface GeoCache {

    href : string;
    
    gcCode: string;
    
    name: string;
    
    coordinates: Point;
    
    type : string;

    placedBy : string;
}

export interface Point {
    type : string;

    coordinates : number[];
}